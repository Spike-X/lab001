package com.aircraft.codelab.labcore.util;

import com.aircraft.codelab.labcore.pojo.entity.LoanContract;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 2021-12-27
 *
 * @author tao.zhang
 * @since 1.0
 */
public class ExcelUtil {
    public static String getPath() {
        // 文件在target/classes下
        return Objects.requireNonNull(ExcelUtil.class.getResource("/")).getPath();
    }

    private static List<LoanContract> data() {
        List<LoanContract> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            LoanContract contract = LoanContract.builder()
                    .userId((long) i)
                    .contractNo("12345")
                    .contractState(1001)
                    .loanProduct("what")
                    .loanAmount(new BigDecimal("10000.00"))
                    .productDetail("holy shit")
                    .createTime(LocalDateTime.now())
                    .build();
            list.add(contract);
        }
        return list;
    }

    public static void main(String[] args) {
        String fileName = getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, LoanContract.class)
                .sheet("模板")
                .doWrite(ExcelUtil::data);
    }
}
