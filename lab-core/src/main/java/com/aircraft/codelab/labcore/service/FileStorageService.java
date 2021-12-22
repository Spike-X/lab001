package com.aircraft.codelab.labcore.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface FileStorageService {
    String save(MultipartFile multipartFile);

    List<String> listFile();

    void load(String filenameUri, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
