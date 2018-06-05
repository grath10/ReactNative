package com.zqtech.smartthermal.model;

// 设备实体类
public class CameraDevice {
    private String ip;
    private String port;
    private String userName;
    private String password;
    private String channel;

    public CameraDevice() {
    }

    public CameraDevice(String ip, String port, String userName, String passWord, String channel) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = passWord;
        this.channel = channel;
    }

    public CameraDevice(String ip, String port, String userName, String passWord) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = passWord;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "[IP=" + ip + "; PORT=" + port + "; USERNAME=" + userName + "; PASSWORD=" + password + "; CHANNEL=" + channel + ";]";
    }
}
