package com.aircraft.codelab.pioneer.util;

import com.aircraft.codelab.pioneer.enums.TaskStateEnum;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * 2021-12-27
 *
 * @author tao.zhang
 * @since 1.0
 */
public class TaskStateConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        TaskStateEnum instance = TaskStateEnum.getInstance(context.getReadCellData().getStringValue());
        return instance.getCode();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        TaskStateEnum instance = TaskStateEnum.getInstance(context.getValue());
        return new WriteCellData<>(instance.getTaskName());
    }
}
