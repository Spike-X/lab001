package com.zt_wmail500.demo.business.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * FWZ_ZM_ZXZM
 *
 * @author
 */
@TableName(value = "FWZ_ZM_ZXZM")//指定表名
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FwzZmZxzm implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    private String id;
    /**
     * 姓名
     */
    @NotBlank
    @Size(min=2, max=30,message = "请检查名字的长度是否有问题")
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
    @NotBlank
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
}