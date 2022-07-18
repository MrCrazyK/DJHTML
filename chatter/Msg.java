package com.k.chatter;

import java.io.Serializable;

public class Msg implements Serializable {
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户发送的消息
     */
    private String message;

    /**
     * 要发给昵称为谁的用户
     */
    private String toWho;

    /**
     * 消息类型
     * 1 登录 -1登出
     * 2 公屏消息 3私聊
     * 4 修改昵称
     */
    private int msgType;

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Msg() {
    }

    public Msg(String toWho, String message) {
        this.message = message;
        this.toWho = toWho;
    }

    public Msg(String userName) {
        this.userName = userName;
        this.msgType = 1;
    }


}
