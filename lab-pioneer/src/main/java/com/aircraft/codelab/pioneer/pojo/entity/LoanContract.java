package com.aircraft.codelab.pioneer.pojo.entity;

import com.aircraft.codelab.pioneer.util.LocalDateTimeConverter;
import com.aircraft.codelab.pioneer.util.TaskStateConverter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@HeadRowHeight(30)
@ContentRowHeight(20)
@ColumnWidth(15)
public class LoanContract implements Serializable {
    /**
     * 主键
     */
    @ExcelIgnore
    private Long id;

    /**
     * 用户表主键id
     */
    @ExcelProperty("用户ID")
    private Long userId;

    /**
     * 借贷合同流水号
     */
    @ExcelProperty(index = 1)// 列
    private String contractNo;

    /**
     * 面签状态：0-待签，1-隐私，2-身份证，3-人脸，4-语音合成，5-语音识别，6-签字，7-结束，8-取消
     */
    @ExcelProperty(value = "面签状态", converter = TaskStateConverter.class)
    private Integer contractState;

    /**
     * 借贷产品‌名称
     */
    @ExcelProperty("产品名称")
    private String loanProduct;

    /**
     * 借贷金额
     */
    @ExcelProperty("*借贷金额")
    @NumberFormat("#,##0.00")
    @DecimalMin(value = "0.01", message = "借贷金额必须在{value}-1000之间")
    @DecimalMax(value = "2000", inclusive = false, message = "借贷金额必须在0.01-{value}之间")//<10000
//    @Digits(integer = 4, fraction = 2, message = "借贷金额必须在0.01-1000之间")
    @Digits(integer = 4, fraction = 2, message = "借贷金额只允许在{integer}位整数和{fraction}位小数范围内")
    private BigDecimal loanAmount;

    /**
     * 产品详情
     */
    @ExcelProperty("产品详情")
    private String productDetail;

    /**
     * 创建时间
     */
    @ColumnWidth(20)
    @ExcelProperty(value = "申请时间", converter = LocalDateTimeConverter.class)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelIgnore
    private LocalDateTime updateTime;

    @ExcelIgnore
    private String errorReason;

    private static final long serialVersionUID = 1L;
}