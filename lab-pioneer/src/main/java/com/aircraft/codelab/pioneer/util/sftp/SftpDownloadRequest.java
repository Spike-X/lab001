package com.aircraft.codelab.pioneer.util.sftp;

import lombok.Data;

/**
 * 2022-11-26
 * Sftp下载请求体
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class SftpDownloadRequest {
    /**
     * sftp上完整文件名
     */
    private String remoteFilePath;
    /**
     * 本地完整文件名
     */
    private String localFilePath;
    /**
     * 文件完成标识
     * 非必选
     */
    private String endFlag;
}
