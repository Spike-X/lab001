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
 * 压缩指定目录下的全部文件
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 压缩指定目录下的全部文件
     *
     * @param zipFile   压缩包文件路径+文件名
     * @param directory 指定文件夹
     * @throws IOException IOException
     */
    public static void compressToZip(String directory, File zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            List<String> filePathList = new ArrayList<>(10);
            collectFilePath(directory, filePathList);
            for (String filePath : filePathList) {
                try (FileInputStream fis = new FileInputStream(filePath);
                        FileChannel fileChannel = fis.getChannel()) {
                    File file = new File(filePath);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    long size = fileChannel.size();
                    fileChannel.transferTo(0, size, writableByteChannel);
                }
            }
            zipOut.flush();
        }
    }

    /**
     * 获取指定文件夹下的全部文件路径
     *
     * @param directory    指定文件夹路径
     * @param filePathList 全部文件路径集合
     */
    private static void collectFilePath(String directory, List<String> filePathList) {
        File root = new File(directory);
        File[] files = root.listFiles();
        assert files != null;
        if (files.length == 0) {
            log.info("empty file directory");
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
                log.error("failed to get file path", ex);
            }
        });
    }


    public static void main(String[] args) throws IOException {
        String path = "D:\\用户\\Downloads\\fgbp-face-factory-0.0.1";
        File zipFile = new File("D:\\用户\\Downloads\\testzip.zip");
        compressToZip(path, zipFile);
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
