package com.k.chatter;

import com.alibaba.fastjson.JSON;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    /**
     * key      昵称
     * value    用户信息
     */
    public static Map<String, User> users = new HashMap<>();

    // 专门处理每一个客户端的输入
    private static void inputHandle(Socket from) {
        // 创建一个线程要执行的任务
        Runnable run = () -> {
            try {
                while (true){
                    // 接收客户端的数据
                    DataInputStream in = new DataInputStream(from.getInputStream());
                    String string = in.readUTF();
                    Msg msg = JSON.parseObject(string, Msg.class);
                    if (msg.getMsgType() == 1){
                        login(msg.getUserName(), from);
                    }else if (msg.getMsgType() == -1){
                        quit(msg.getUserName());
                        break;
                    }else{
                        sendMessage(from, msg);
                    }
                }
            } catch (IOException ignored) {

            }
        };
        // 创建子线程 并启动
        Thread t = new Thread(run);
        t.start();
    }

    /**
     * 登录一名用户的方法
     * @param userName 用户名
     * @param from  用户来源
     */
    private static boolean login(String userName, Socket from) {
        String ip = from.getInetAddress().getHostAddress();
        User user = new User(userName, ip, from);
        if (users.containsKey(userName)){
            return false;
        } else {
            users.put(userName,user);
            return true;
        }
    }

    private static void quit(String userName) {
        users.remove(userName);
    }

    //  3.实现将消息发送到接收方,并返回状态的方法
    private static void sendMessage(Socket from, Msg msg) throws IOException {
        // 找到发送信息的主机昵称
        String returning;
        if (!users.containsKey(msg.getToWho())) {
            returning = "对方昵称不存在或者已下线";
        } else {
            //根据昵称找到要发送给的主机
            Socket to = users.get(msg.getToWho()).getPort();
            String ip = from.getInetAddress().getHostAddress();
            // 发送给客户端数据
            DataOutputStream toOut = new DataOutputStream(to.getOutputStream());
            System.out.println(msg.getUserName() + "(" + ip + ")对" + msg.getToWho() + "说：" + msg.getMessage());
            toOut.writeUTF(msg.getUserName() + "(" + ip + ")对你说:" + msg.getMessage());
            returning = "发送成功";
        }
        DataOutputStream fromOut = new DataOutputStream(from.getOutputStream());
        fromOut.writeUTF(returning);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            // 等待client的请求
            System.out.println("waiting...");
            Socket client = server.accept();
            inputHandle(client);
        }
    }
}