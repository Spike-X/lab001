package com.aircraft.codelab.labcore.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.labcore.service.FileProperties;
import com.aircraft.codelab.labcore.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public String save(MultipartFile multipartFile) {
        // 默认目录
        Path baseLocation = Paths.get(fileProperties.getUploadDir());
        try {
            // 创建文件目录
            String dateTime = DateUtil.getDate("yyyy/MM/dd");
            Path directories = baseLocation.resolve(dateTime);
            boolean notExists = Files.notExists(directories);
            if (notExists) {
                directories = Files.createDirectories(directories);
            }

            // 源文件名
            String originalFilename = multipartFile.getOriginalFilename();
            log.debug(originalFilename);
            // 主文件名
            String mainName = FileNameUtil.mainName(originalFilename);
            // 扩展名
            String extensionName = FileNameUtil.extName(originalFilename);
            String fileName = IdUtil.simpleUUID();
            String newFileName = StringUtils.isBlank(extensionName) ? fileName : fileName + "." + extensionName;
            Path targetLocation = directories.resolve(newFileName).normalize();

            // 写文件
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            // 相对路径
            Path normalize = baseLocation.relativize(targetLocation).normalize();
            return normalize.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file Please try again!", ex);
        }
    }

    @Override
    public List<String> listFile() {
        Path baseLocation = Paths.get(fileProperties.getUploadDir());
        try (Stream<Path> paths = Files.walk(baseLocation)) {
            return paths.filter(Files::isRegularFile)
                    .map(baseLocation::relativize)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

    @Override
    public void load(String filenameUri, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path rootPath = Paths.get(fileProperties.getUploadDir());
        // 数据库查询文件地址,拼接rootPath
        Path filePath = rootPath.resolve(filenameUri);
        Resource resource = new UrlResource(filePath.toUri());
        try (InputStream is = resource.getInputStream();
             BufferedInputStream bis = IOUtils.buffer(is);
             BufferedOutputStream bos = IOUtils.buffer(response.getOutputStream())) {
            if (!resource.isReadable()) {
                throw new RuntimeException("Could not read the file.");
            }
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + resource.contentLength());
            String fileName = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8.displayName());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            IOUtils.copyLarge(bis, bos);
            bos.flush();
        }
    }
}
