package com.zt_wmail500.demo.business.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * FWZ_ZM_ZXZM
 * @author 
 */
@TableName(value = "FWZ_ZM_ZXZM")//指定表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FwzZmZxzm implements Serializable {

    @NotNull
    private String id;

    /**
     * 姓名
     */
    @NotBlank
    @Length(max = 20)
    private String name;

    /**
     * 入校时间
     */
    private Integer rxsj;

    /**
     * 性别
     */
    private String xb;

    /**
     * 学号，学生唯一代码
     */
    private String xh;

    /**
     * 身份证号
     */
    private String sfzh;

    /**
     * 院系
     */
    private String yx;

    /**
     * 专业
     */
    private String zy;

    /**
     * 学制
     */
    private Integer xz;

    /**
     * 学院
     */
    private String xy;

    /**
     * 学校名称
     */
    private String xxmc;

    private static final long serialVersionUID = 1L;
}