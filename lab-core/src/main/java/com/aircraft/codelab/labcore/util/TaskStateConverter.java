package com.aircraft.codelab.labcore.util;

import com.aircraft.codelab.labcore.enums.TaskState;
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
        TaskState instance = TaskState.getInstance(context.getReadCellData().getStringValue());
        return instance.getCode();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        TaskState instance = TaskState.getInstance(context.getValue());
        return new WriteCellData<>(instance.getTaskName());
    }
}
