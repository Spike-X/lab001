package com.aircraft.codelab.pioneer.util.sftp;

import lombok.Data;

/**
 * 2022-11-26
 * Sftp配置
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class SftpConfig {
    /**
     * sftp 服务器地址
     */
    private String host;
    /**
     * sftp 服务器端口
     */
    private int port;
    /**
     * sftp服务器登陆用户名
     */
    private String user;
    /**
     * sftp 服务器登陆密码
     * 密码和私钥二选一
     */
    private String password;
    /**
     * 私钥文件
     * 私钥和密码二选一
     */
    private String identity;
}
