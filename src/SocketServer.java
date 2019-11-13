
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @project: untitled
 * @author: wangchengda
 * @email: wangchengda1990@gmail.com
 * @date: 2019/11/13
 * @Description:
 * @Copyright: 2019 broderickwang.github.io . All rights reserved.
 * @version: V1.0
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        int port = 53333;

        ServerSocket sever = new ServerSocket(port);

        System.out.println("server is waiting messages!");

        Socket socket = sever.accept();

        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[1024];
        int len;

        StringBuffer sb = new StringBuffer();

        while ((len=inputStream.read(bytes))!=-1){
            sb.append(new String(bytes,0,len,"UTF-8"));
        }
        System.out.println("get messages from client is : "+sb.toString());

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("Hello Client , I got the messages!".getBytes("UTF-8"));

        inputStream.close();
        outputStream.close();
        socket.close();
        sever.close();
    }
}
