import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 53333;

        Socket socket = new Socket(host,port);

        OutputStream outputStream = socket.getOutputStream();
        String message = "你好，Seven鑫洋";
        outputStream.write(message.getBytes());
//        outputStream.close();\

//        socket.close();

        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;

        StringBuffer sb = new StringBuffer();
        while ((len=inputStream.read(bytes))!=-1){
            sb.append(new String(bytes,0,len,"UTF-8"));
        }
        System.out.println("client get message from server is : "+sb.toString());
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
