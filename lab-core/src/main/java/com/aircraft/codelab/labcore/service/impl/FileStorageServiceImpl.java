package com.aircraft.codelab.labcore.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.labcore.service.FileProperties;
import com.aircraft.codelab.labcore.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        try {
            // 默认目录
            Path baseLocation = Paths.get(fileProperties.getUploadDir());
            // 创建文件目录
            String dateTime = DateUtil.getDate("yyyy/MM/dd");
            Path directories = Files.createDirectories(baseLocation.resolve(dateTime));

            // 源文件名
            String originalFilename = multipartFile.getOriginalFilename();
            // 主文件名
            String mainName = FileNameUtil.mainName(originalFilename);
            log.debug(mainName);
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
    public Resource load(String filename) {
        return null;
    }

    @Override
    public Stream<Path> load() {
        return null;
    }
}
