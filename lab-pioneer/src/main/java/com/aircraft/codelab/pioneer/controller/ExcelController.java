package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.core.util.ValidateUtil;
import com.aircraft.codelab.pioneer.mapper.LoanContractMapper;
import com.aircraft.codelab.pioneer.pojo.entity.LoanContract;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 2023-02-07
 * 导入导出
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "导入导出")
@RequestMapping("/excel")
public class ExcelController {

    @ApiOperation(value = "测试导出")
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportData(HttpServletResponse response) throws Exception {

        /*response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = URLEncoder.encode("文件模板", StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName + ".xlsx");*/

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = URLEncoder.encode("导入派单" + ".xlsx", StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        List<LoanContract> contractList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            LoanContract contract = LoanContract.builder()
                    .userId((long) i + 1)
                    .contractNo("12345" + i)
                    .contractState(1001)
                    .loanProduct("what")
                    .loanAmount(new BigDecimal("10000.00"))
                    .productDetail("holy shit")
                    .createTime(LocalDateTime.now())
                    .build();
            contractList.add(contract);
        }

        /*EasyExcel.write(response.getOutputStream())
                .head(LoanContract.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("合同模板")
                .doWrite(contractList);*/

        //获取模板
        File file = new File("D:\\home\\导入派单模板.xlsx");
        try (InputStream inputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            /*EasyExcel.write(outputStream, LoanContract.class)
                    .withTemplate(inputStream)
                    .sheet()
                    .autoTrim(true)
                    .needHead(false)
                    .doWrite(contractList);*/
            //设置输出流和模板信息
            ExcelWriter excelWriter = EasyExcel.write(outputStream, LoanContract.class)
                    .withTemplate(inputStream).needHead(Boolean.FALSE).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 分批写入excel
            List<List<LoanContract>> partition = Lists.partition(contractList, 3);
            for (List<LoanContract> loanContracts : partition) {
                int size = loanContracts.size();
                log.info("本次写入数据: {}条", size);
                excelWriter.write(loanContracts, writeSheet);
            }
            excelWriter.finish();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Resource
    private LoanContractMapper loanContractMapper;

    @ApiOperation(value = "测试导入")
    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<List<LoanContract>> importData(@RequestPart("file") MultipartFile file) throws IOException {
        /*EasyExcelContractListener easyExcelContractListener = new EasyExcelContractListener(loanContractMapper);
        // 异步读取
        EasyExcel.read(file.getInputStream(), LoanContract.class, easyExcelContractListener)
                .sheet()
                .headRowNumber(2)
                .doRead();
        List<LoanContract> data = easyExcelContractListener.getData();*/

        ExcelContractListener excelContractListener = new ExcelContractListener();
        EasyExcel.read(file.getInputStream(), LoanContract.class, excelContractListener)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet()
                .headRowNumber(2)
                .doRead();
        List<LoanContract> data = excelContractListener.getData();

        // 同步读取
        /*List<LoanContract> contractList = EasyExcel.read(file.getInputStream())
                .head(LoanContract.class)
                .sheet()
                .headRowNumber(2)
                .doReadSync();

        for (LoanContract loanContract : contractList) {
            try {
                ValidateUtil.validate(loanContract);
            } catch (Exception e) {
                loanContract.setErrorReason(e.getMessage());
                log.error(e.getMessage(), e);
            }
        }*/
        return CommonResult.success(ResultCode.SUCCESS.getMessage());
    }
}
