package com.aircraft.codelab.labcore.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.labcore.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
@RestController
@Slf4j
public class FileController {
    @Resource
    private FileStorageService fileStorageService;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<String> upload(@RequestParam("file") MultipartFile file) {
        String save = fileStorageService.save(file);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), save);
    }

    @GetMapping(value = "/listFiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<List<String>> listFiles() {
        List<String> list = fileStorageService.listFile();
        return CommonResult.success(list);
    }

    @GetMapping(value = "/downloadFile/{filenameUri:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadFile(@PathVariable String filenameUri, HttpServletResponse response) {
        log.debug(filenameUri);
        try {
            fileStorageService.load(filenameUri, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
