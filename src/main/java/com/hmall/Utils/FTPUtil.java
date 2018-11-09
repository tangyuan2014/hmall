package com.hmall.Utils;


import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FTPUtil {

    private static String ftpIp = PropsUtil.getProerty("ftp.server.ip","192.168.56.101");
    private static String ftpUser = PropsUtil.getProerty("ftp.user","ftpuser");
    private static String ftpPassword = PropsUtil.getProerty("ftp.pass","123456");


    private static final Logger logger=LoggerFactory.getLogger(FTPUtil.class);

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }



    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21,ftpUser , ftpPassword );
        logger.info("start uploading to FTP server");
        boolean result=ftpUtil.uploadFile("img",fileList);
        logger.info("start connecting to FTP server,finish uploading,result is:{}");
        return result;

    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean upload = true;
        FileInputStream fis = null;
        if(connectServer(this.getIp(),this.getPort(),this.getUser(),this.getPwd())){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for(File fileItem:fileList){
                    fis=new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                logger.error("uploading exception",e);
                upload=false;
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return upload;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {

        boolean isSuccess=false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess=ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("CONNECT ftp server exception",e);
        }
        return isSuccess;
    }
}
