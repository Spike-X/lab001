package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.pioneer.pojo.entity.LoanContract;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 2023-03-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class ExcelContractListener extends AnalysisEventListener<LoanContract> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    private static final Map<Integer, String> MAP = Maps.newHashMap();

    static {
        MAP.put(0, "*用户ID");
        MAP.put(4, "*借贷金额");
    }

    /**
     * 缓存的数据
     */
    private final List<LoanContract> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        // 获取总行数（含表头）
        Integer rowNumber = context.readSheetHolder().getApproximateTotalRowNumber();
        if (rowNumber > 100) {
            throw new RuntimeException("导入数据量超过限制");
        }
        Integer rowIndex = context.readRowHolder().getRowIndex();
        // 第二行
        if (rowIndex == 1) {
            // 第一列
            String s1 = headMap.get(0);
            // 第五列
            String s5 = headMap.get(4);
            if (!(MAP.get(0).equals(s1) && MAP.get(4).equals(s5))) {
                throw new RuntimeException("表头异常");
            }
        }
    }

    @Override
    public void invoke(LoanContract loanContract, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(loanContract));
        // 获取总行数（含表头）
        Integer rowNumber = analysisContext.readSheetHolder().getApproximateTotalRowNumber();
        Integer rowIndex = analysisContext.readRowHolder().getRowIndex();
        // 这里rowIndex==2 是第一行数据 以表头开始算起
        cachedDataList.add(loanContract);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！共计{}条", cachedDataList.size());
    }

//    /**
//     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
//     *
//     * @param exception
//     * @param context
//     * @throws Exception
//     */
//    @Override
//    public void onException(Exception exception, AnalysisContext context) {
//        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
//        // 如果是某一个单元格的转换异常 能获取到具体行号
//        // 如果要获取头的信息 配合invokeHeadMap使用
//        if (exception instanceof ExcelDataConvertException) {
//            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
//            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
//                    excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());
//        } else {
//            throw new RuntimeException(exception.getMessage(), exception);
//        }
//    }
    public List<LoanContract> getData() {
        return cachedDataList;
    }
}
