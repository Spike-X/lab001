package com.aircraft.codelab.pioneer.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2021-12-27
 *
 * @author tao.zhang
 * @since 1.0
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadConverterContext<?> context) {
        return LocalDateTime.parse(context.getReadCellData().getStringValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<LocalDateTime> context) {
        return new WriteCellData<>(context.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}


