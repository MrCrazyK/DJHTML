package com.k.chatter;
import java.net.Socket;
public class User {
    /**
     * 昵称
     */
    private String userName;
    /**
     * ip地址
     */
    private String ip;
    /**
     * port端口
     */
    private Socket port;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Socket getPort() {
        return port;
    }

    public void setPort(Socket port) {
        this.port = port;
    }

    public User(String userName, String ip, Socket port) {
        this.userName = userName;
        this.ip = ip;
        this.port = port;
    }
}
