package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.pioneer.mapper.LoanContractMapper;
import com.aircraft.codelab.pioneer.pojo.entity.LoanContract;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 2023-02-21
 * EasyExcel监听器
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class EasyExcelContractListener implements ReadListener<LoanContract> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 8;

    /**
     * 缓存的数据
     */
    private List<LoanContract> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private final LoanContractMapper contractMapper;

    public EasyExcelContractListener(LoanContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    public void invoke(LoanContract loanContract, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(loanContract));
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        cachedDataList.add(loanContract);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
//        contractMapper.insertBatch(cachedDataList);
        log.info("存储数据库成功！");
    }
}
