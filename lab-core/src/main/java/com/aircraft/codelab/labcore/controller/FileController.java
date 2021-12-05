package com.aircraft.codelab.labcore.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.labcore.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
@RestController
public class FileController {
    @Resource
    FileStorageService fileStorageService;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String save = fileStorageService.save(file);
            return CommonResult.success(save);
        } catch (Exception e) {
            return CommonResult.failed();
        }
    }
}
