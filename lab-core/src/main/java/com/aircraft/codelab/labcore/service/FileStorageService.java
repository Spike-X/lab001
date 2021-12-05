package com.aircraft.codelab.labcore.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface FileStorageService {
    String save(MultipartFile multipartFile);

    Resource load(String filename);

    Stream<Path> load();
}
