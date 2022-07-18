package com.k.chatter;

import com.alibaba.fastjson.JSON;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String serverName = "server";
    private static String myName;
    private static Socket socket;
    private static String serverIP = "127.0.0.1";
    private static int port = 8080;
    //接受数据的方法
    static void receiverMessage(){
        //开个线程
        new Thread(() -> {
            //获取数据得到输入流
            try {
                while (true){
                    if(socket != null){
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        System.out.println(in.readUTF());
                    }else {
                        break;
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }).start();
    }
    //发送消息的方法
    //将控制台输入的内容发给服务器
    private static void sendToService(){
        //创建键盘扫描器 System.in标准输入流 一般不关闭 他是系统打开的
        Scanner scanner = new Scanner(System.in);
        //发送到服务器
        try {
            while(true){
                System.out.println("输入发言昵称:");
                String p = scanner.nextLine();
                if (p.equals("over")) {
                    System.out.println("聊天结束");
                    break;
                }
                System.out.println("输入发言内容:");
                String m = scanner.nextLine();
                Msg msg = new Msg(p,m);
                msg.setMsgType(3);
                msg.setUserName(myName);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(JSON.toJSONString(msg));
            }
            // 注意 调用这个方法会立即断开连接
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        socket = new Socket(serverIP, port);
        System.out.println("连接成功 请先输入你的昵称开始聊天");
        Scanner scanner = new Scanner(System.in);
        myName = scanner.nextLine();
        //用户登录
        Msg msg = new Msg(myName);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(JSON.toJSONString(msg));
        //开启线程程接收消息
        receiverMessage();
        //开始发送消息
        sendToService();
    }
}
