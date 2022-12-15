package com.aircraft.codelab.pioneer.pojo.vo;

import lombok.Data;

/**
 * 2022-12-12
 * 文件属性信息
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class FileVo {
    private int returnCode;
    private String returnMsg;
    private String id;
    private String enterpriseName;
    private String address;
    private String valuation;
}
