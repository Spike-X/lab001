package com.aircraft.codelab.pioneer.util.sftp;

import com.alibaba.fastjson.JSON;

import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Vector;

/**
 * 2022-11-26
 * Sftp客户端
 *
 * @author tao.zhang
 * @since 1.0
 */
public class SftpClient {
    public boolean downloadFile(SftpConfig sftpConfig, SftpDownloadRequest request) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSession(sftpConfig);
            channelSftp = getChannelSftp(session);

            String remoteFileDir = getRemoteFileDir(request.getRemoteFilePath());
            String remoteFileName = getRemoteFileName(request.getRemoteFilePath());
            // 校验SFTP上文件是否存在
            if (!isFileExist(channelSftp, remoteFileDir, remoteFileName, request.getEndFlag())) {
                return false;
            }

            // 切换到SFTP文件目录
            channelSftp.cd(remoteFileDir);

            // 下载文件
            File localFile = new File(request.getLocalFilePath());
            // 创建多级目录
            boolean mkdirs = localFile.mkdirs();
            // 先删除本地同名文件或目录
            FileUtils.deleteQuietly(localFile);
            channelSftp.get(remoteFileName, request.getLocalFilePath());

            return true;
        } catch (JSchException jSchException) {
            throw new RuntimeException("sftp connect failed:" + JSON.toJSONString(sftpConfig), jSchException);
        } catch (SftpException sftpException) {
            throw new RuntimeException("sftp download file failed:" + JSON.toJSONString(request), sftpException);
        } finally {
            disconnect(channelSftp, session);
        }
    }

    public void uploadFile(SftpConfig sftpConfig, SftpUploadRequest request) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSession(sftpConfig);
            channelSftp = getChannelSftp(session);

            String remoteFileDir = getRemoteFileDir(request.getRemoteFilePath());
            String remoteFileName = getRemoteFileName(request.getRemoteFilePath());

            // 切换到SFTP文件目录
            cdOrMkdir(channelSftp, remoteFileDir);

            // 上传文件
            channelSftp.put(request.getLocalFilePath(), remoteFileName);
            if (StringUtils.isNoneBlank(request.getEndFlag())) {
                channelSftp.put(request.getLocalFilePath() + request.getEndFlag(),
                        remoteFileName + request.getEndFlag());
            }
        } catch (JSchException jSchException) {
            throw new RuntimeException("sftp connect failed: " + JSON.toJSONString(sftpConfig), jSchException);
        } catch (SftpException sftpException) {
            throw new RuntimeException("sftp upload file failed: " + JSON.toJSONString(request), sftpException);
        } finally {
            disconnect(channelSftp, session);
        }
    }

    private Session getSession(SftpConfig sftpConfig) throws JSchException {
        Session session;
        JSch jsch = new JSch();
        if (StringUtils.isNoneBlank(sftpConfig.getIdentity())) {
            jsch.addIdentity(sftpConfig.getIdentity());
        }
        if (sftpConfig.getPort() <= 0) {
            // 默认端口
            session = jsch.getSession(sftpConfig.getUser(), sftpConfig.getHost());
        } else {
            // 指定端口
            session = jsch.getSession(sftpConfig.getUser(), sftpConfig.getHost(), sftpConfig.getPort());
        }
        if (StringUtils.isNoneBlank(sftpConfig.getPassword())) {
            session.setPassword(sftpConfig.getPassword());
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.setTimeout(10 * 1000); // 设置超时时间10s
        session.connect();

        return session;
    }

    private ChannelSftp getChannelSftp(Session session) throws JSchException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        return channelSftp;
    }

    /**
     * SFTP文件是否存在
     * true：存在；false：不存在
     */
    private boolean isFileExist(ChannelSftp channelSftp,
                                String fileDir,
                                String fileName,
                                String endFlag) throws SftpException {
        if (StringUtils.isNoneBlank(endFlag)) {
            if (!isFileExist(channelSftp, fileDir, fileName + endFlag)) {
                return false;
            }
        } else {
            if (!isFileExist(channelSftp, fileDir, fileName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * SFTP文件是否存在
     * true：存在；false：不存在
     */
    private boolean isFileExist(ChannelSftp channelSftp,
                                String fileDir,
                                String fileName) throws SftpException {
        if (!isDirExist(channelSftp, fileDir)) {
            return false;
        }
        Vector vector = channelSftp.ls(fileDir);
        for (int i = 0; i < vector.size(); ++i) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) vector.get(i);
            if (fileName.equals(entry.getFilename())) {
                return true;
            }
        }
        return false;
    }

    /**
     * sftp上目录是否存在
     * true：存在；false：不存在
     */
    private boolean isDirExist(ChannelSftp channelSftp, String fileDir) {
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(fileDir);
            return sftpATTRS.isDir();
        } catch (SftpException e) {
            return false;
        }
    }

    private void cdOrMkdir(ChannelSftp channelSftp, String fileDir) throws SftpException {
        if (StringUtils.isBlank(fileDir)) {
            return;
        }

        for (String dirName : fileDir.split(File.separator)) {
            if (StringUtils.isBlank(dirName)) {
                dirName = File.separator;
            }
            if (!isDirExist(channelSftp, dirName)) {
                channelSftp.mkdir(dirName);
            }
            channelSftp.cd(dirName);
        }
    }

    private String getRemoteFileDir(String remoteFilePath) {
        int remoteFileNameindex = remoteFilePath.lastIndexOf(File.separator);
        return remoteFileNameindex == -1
                ? ""
                : remoteFilePath.substring(0, remoteFileNameindex);
    }


    private String getRemoteFileName(String remoteFilePath) {
        int remoteFileNameindex = remoteFilePath.lastIndexOf(File.separator);
        /*if (remoteFileNameindex == -1) {
            return remoteFilePath;
        }*/

        String remoteFileName = remoteFileNameindex == -1
                ? remoteFilePath
                : remoteFilePath.substring(remoteFileNameindex + 1);
        if (StringUtils.isBlank(remoteFileName)) {
            throw new RuntimeException("remoteFileName is blank");
        }

        return remoteFileName;
    }

    private void disconnect(ChannelSftp channelSftp, Session session) {
        if (channelSftp != null) {
            channelSftp.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}
