import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class test{
    public static void main(String[] args){
        // System.out.println("Hello,world!");
        //开启socket 参数：端口号
        ServerSocket server = new ServerSocket(8080);

        Socket socket = server.accept();
        
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String string = in.readUTF();//接收结果
    }
}