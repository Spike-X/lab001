package com.aircraft.codelab.labcore.file;

import com.aircraft.codelab.labcore.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 2022-08-26
 * Zip4j
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class ZipTest {
    @Test
    void zip4j() throws ZipException {
        File file1 = new File("D:\\用户\\Downloads\\RW20220620000276\\杭州众森舞台设计有限公司-企业LOGO挂牌+门牌号_0.jpg");
        File file2 = new File("D:\\用户\\Downloads\\RW20220620000276\\杭州众森舞台设计有限公司-企业办公场所内的办公场景_0.jpg");
        File file3 = new File("D:\\用户\\Downloads\\RW20220620000276\\杭州众森舞台设计有限公司-企业办公场所内的办公场景_1.jpg");
        File file4 = new File("D:\\用户\\Downloads\\RW20220620000276\\杭州众森舞台设计有限公司-企业办公场所外景_0.jpg");
        File file5 = new File("D:\\用户\\Downloads\\RW20220620000276\\杭州众森舞台设计有限公司-企业办公场所外景_1.jpg");
        List<File> fileList = Lists.newArrayList(file1, file2, file3, file4, file5);
        File zipFile1 = new File("D:\\用户\\Downloads\\temp\\filename.zip");
        if (!zipFile1.exists() && !zipFile1.isDirectory()) {
            zipFile1.mkdirs();
        }
        if (zipFile1.exists()) {
            zipFile1.delete();
        }
        ZipFile zipFile = new ZipFile("D:\\用户\\Downloads\\temp\\filename.zip");
        // 10M = 10 * 1024 * 1024 = 10485760B
        zipFile.createSplitZipFile(fileList, new ZipParameters(), true, 2097152);
        List<String> filePathList = new ArrayList<>(10);
        FileUtil.collectFilePath("D:\\用户\\Downloads\\temp", filePathList);
        log.debug("filePathList: {}", JSON.toJSONString(filePathList));
        List<File> zipFileList = new ArrayList<>(10);
        filePathList.forEach(filePath -> {
            File file = new File(filePath);
            zipFileList.add(file);
        });
        zipFileList.forEach(file -> System.out.println(file.getName()));
    }

    @Test
    void unzip() throws ZipException {
        ZipFile zipFile = new ZipFile("D:\\用户\\Downloads\\temp\\20220607建行对接测试001.zip");
        zipFile.extractAll("D:\\用户\\Downloads\\temp");
    }
}
