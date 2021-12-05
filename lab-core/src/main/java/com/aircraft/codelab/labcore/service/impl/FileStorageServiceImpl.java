package com.aircraft.codelab.labcore.service.impl;

import com.aircraft.codelab.core.util.DateUtil;
import com.aircraft.codelab.labcore.service.FileProperties;
import com.aircraft.codelab.labcore.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
@EnableConfigurationProperties(FileProperties.class)
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public String save(MultipartFile multipartFile) {
        try {
            Path baseLocation = Files.createDirectories(Paths.get(fileProperties.getUploadDir()));

            String dateTime = DateUtil.getDateTime();
            String originalFilename = multipartFile.getOriginalFilename();

            String fileName = UUID.randomUUID().toString();
            Path targetLocation = baseLocation.resolve(dateTime).resolve(fileName);

            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
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
