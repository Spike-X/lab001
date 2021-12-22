package com.aircraft.codelab.labcore.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 2021-06-17
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    public void compressToZip(File zipFile, String path) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            List<String> filePathList = new ArrayList<>();
            collectFilePath(path, filePathList);
            for (String filePath : filePathList) {
                try (FileChannel fileChannel = new FileInputStream(filePath).getChannel()) {
                    File file = new File(filePath);
                    zipOut.putNextEntry(new ZipEntry(file.getName()));
                    long size = fileChannel.size();
                    fileChannel.transferTo(0, size, writableByteChannel);
                }
            }
        }
    }

    private void collectFilePath(String path, List<String> filePathList) {
        File root = new File(path);
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        Arrays.stream(files).forEach(file -> {
            try {
                if (file.isDirectory()) {
                    collectFilePath(file.getCanonicalPath(), filePathList);
                } else {
                    filePathList.add(file.getCanonicalPath());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void fileStreamResponse(File zipFile, HttpServletResponse response) throws IOException {
        try (FileInputStream fis = FileUtils.openInputStream(zipFile);
             BufferedInputStream bis = IOUtils.buffer(fis);
             BufferedOutputStream bos = IOUtils.buffer(response.getOutputStream())) {
            String fileName = URLEncoder.encode(zipFile.getName(), StandardCharsets.UTF_8.displayName());

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            response.setHeader(HttpHeaders.CONTENT_LENGTH, "" + zipFile.length());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            IOUtils.copyLarge(bis, bos);
            bos.flush();
        }
    }
}
