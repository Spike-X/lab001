package com.aircraft.codelab.pioneer.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 2022-09-01
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class FileTest {
    @Test
    @DisplayName("文件测试")
    void fileTest() throws ZipException {
        File file = FileUtil.mkdir("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp");
        // 仅创建目录 文件名也会变目录
        File file1 = FileUtil.mkdir("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\filename.zip");

        String mimeType = FileUtil.getMimeType("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\IMG_0678.JPG");
        log.debug("mimeType: {}", mimeType);

        File file2 = new File("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\2.png");
        // D:\Users\zt_wm\Downloads\aTemp\bTemp\2.png
        String path = file2.getPath();
        // D:\Users\zt_wm\Downloads\aTemp\bTemp
        String parent = file2.getParent();
        // D:\Users\zt_wm\Downloads\aTemp
        String parent1 = FileUtil.getParent("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\2.png", 2);
        log.debug("{},\n{},\n{}", path, parent, parent1);

        // IMG_0678
        String filename = FileUtil.mainName("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\IMG_0678.JPG");
        // JPG
        String extname = FileUtil.extName("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp\\IMG_0678.JPG");
        log.debug("filename: {}, extname: {}", filename, extname);

        File file3 = new File("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp");
        // 遍历目录下的所有文件和目录，递归计算其大小
        long size = FileUtil.size(file3);
        String filesize = NumberUtil.decimalFormatMoney(size / 1024.00 / 1024.00);
        log.debug("filesize: {}(MB)", filesize);

        // 获得指定目录下所有文件的文件名
        List<String> fileNames = FileUtil.listFileNames("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp");
        log.debug("fileNames: {}", fileNames);

        // 列出当前目录下的目录和文件路径
        File[] files = FileUtil.ls("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp");
        File[] files1 = new File("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp").listFiles();
        // 筛选出是文件的路径
        List<String> filePath = Arrays.stream(files).map(f -> {
            try {
                if (f.isFile()) {
                    return f.getCanonicalPath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        log.debug("filePath1: {}", filePath);

        List<File> fileList = FileUtil.loopFiles("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp");
        List<File> fileList1 = FileUtil.loopFiles("D:\\Users\\zt_wm\\Downloads\\aTemp\\bTemp", f -> f.getName().endsWith("txt"));
        List<String> list = fileList.stream().map(f -> {
            try {
                return f.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        log.debug("filePath2: {}", list);

//        ZipFile zipFile = new ZipFile("D:\\Users\\zt_wm\\Downloads\\aTemp\\filename.zip");
//        // 10M = 10 * 1024 * 1024 = 10485760B
//        zipFile.createSplitZipFile(fileList, new ZipParameters(), true, 2097152);
    }

    @Test
    void emojiTest() {
        String str = "An 😀awesome 😃string with a few 😉emojis!";
        log.debug(str);
        if (EmojiUtil.containsEmoji(str)) {
            String toHtml = EmojiUtil.toHtml(str);
            // An &#x1f600;awesome &#x1f603;string with a few &#x1f609;emojis!
            log.debug(toHtml);
            String unicode = EmojiUtil.toUnicode(toHtml);
            log.debug(unicode);
        }
    }

    @Test
    void createDirIfNotExist() {
        // 创建父目录
        File localFile = new File("D:\\Users\\zt_wm\\Downloads\\sftp\\bTemp\\IMG_0678.JPG");
        boolean mkdirs = localFile.mkdirs();
        FileUtils.deleteQuietly(localFile);
    }
}
