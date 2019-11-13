/**
 * @project: untitled
 * @author: wangchengda
 * @email: wangchengda1990@gmail.com
 * @date: 2019/11/13
 * @Description:
 * @Copyright: 2019 broderickwang.github.io . All rights reserved.
 * @version: V1.0
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestHeader {
    /**
     * 组成	中文名称	英文名称	长度/byte	取值或说明
     * 消息头	开始标志	StartSign	2	固定为0xFFFF，消息开始标识
     * 消息类型	msgType	1	单字节整型数，类型编码含义如下：
     * 0:realTime
     * 1:reqLogin
     * 2:ackLogin
     * 3:reqSyncMsg
     * 4:ackSyncMsg
     * 8:reqHeartBeat
     * 9:ackHeartBeat
     * 10:closeConnAlarm
     * 秒时间戳	timeStamp	4	4字节整型数，字节顺序为Big-Endian，表示消息产生时间,为距离1970-01-01 00:00:00时间偏移的秒数。
     * 长度	lenOfBody	2	2字节整型数，字节顺序为Big-Endian，表示消息体字节长度，取值范围0~32767。
     * 消息体	具体消息内容。对于上报的消息realTime，消息体中只包括一条json格式的数据。
     */

    /**
     * Big-Endian,将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     */
    public static byte[] intToBytes2(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }


    /**
     * Big-Endian,将short数值转换为占二个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     */
    public static byte[] shortToBytes2(short value)
    {
        byte[] src = new byte[2];
        src[0] = (byte) ((value>>8)&0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    public static short bytesToShort2(byte[] src, int offset) {
        short value;
        value = (short) (
                ((src[offset] & 0xFF)<<8)
                        |(src[offset+1] & 0xFF));
        return value;
    }

    /**
     * 单字节处理为字节数组
     */
    public static byte[] byteToBytes2(byte value){
        byte[] src = new byte[1];
        src[0] = value;
        return src;
    }

    public static byte bytesToByte(byte[] src, int offset) {
        byte value;
        value =  (src[offset]);
        return value;
    }


    public static void main(String[] args) throws IOException {

        myHead("reqLogin;user=yiy;key=qw#$@;type=msg");
        //拼接
       /* String strBody = "reqLogin;user=yiy;key=qw#$@;type=msg";
        byte[] allMessage = new byte[9+strBody.length()];
        short startSign = (short) 0xFFFF;
        byte msgType = 1;
        int timeStamp = (int) (System.currentTimeMillis()/1000);
        short bodyLen = (short) strBody.length();
        System.arraycopy(shortToBytes2(startSign),0, allMessage,0,2);
        System.arraycopy(byteToBytes2(msgType),0, allMessage,2,1);
        System.arraycopy(intToBytes2(timeStamp),0, allMessage,3,4);
        System.arraycopy(shortToBytes2(bodyLen),0, allMessage,7,2);
        System.arraycopy(strBody.getBytes(),0, allMessage,9,strBody.length());

        System.out.println("startSign="+startSign+",msgType="+msgType+",timeStamp="+timeStamp+",bodyLen="+bodyLen+",strBody="+strBody);*/

        //生成文件确认内容
        /*File f = new File("d:" + File.separator + "test.txt");
        OutputStream out = null;
        out = new FileOutputStream(f);
        out.write(allMessage);
        out.close();

        //解析
        short startSignParser = bytesToShort2(allMessage,0);
        byte msgTypeParser = bytesToByte(allMessage,2);
        int timeStampParser = bytesToInt2(allMessage,3);
        short bodyLenParser =  bytesToShort2(allMessage,7);
        byte[] bodyParser = new  byte[bodyLenParser];
        System.arraycopy(allMessage,9,bodyParser,0,bodyLenParser);
        String bodyParserStr = new String(bodyParser);

        System.out.println("startSignParser="+startSignParser+",msgTypeParser="+msgTypeParser+",timeStampParser="+timeStampParser+",bodyLenParser="+bodyLenParser+",bodyParserStr="+bodyParserStr);
*/
    }

    public static void myHead(String strBody){
        byte[] allMessage = new byte[20+strBody.length()];

        String magic = "QYZN";
        System.arraycopy(magic.getBytes(),0, allMessage,0,4);
        byte version = 2;
        System.arraycopy(byteToBytes2(version),0, allMessage,4,1);
        byte msgType = 4;
        System.arraycopy(byteToBytes2(msgType),0, allMessage,5,1);
        String dataType = "json";
        System.arraycopy(dataType.getBytes(),0, allMessage,6,1);
        byte resv = 1;
        System.arraycopy(byteToBytes2(resv),0, allMessage,7,1);
        int timeStamp = (int) (System.currentTimeMillis()/1000);
        System.arraycopy(intToBytes2(timeStamp),0, allMessage,8,4);
        int seq = 1;
        System.arraycopy(intToBytes2(seq),0, allMessage,12,4);
        int dataSize = (short) strBody.length();
        System.arraycopy(intToBytes2(dataSize),0, allMessage,16,4);

        System.arraycopy(strBody.getBytes(),0, allMessage,20,strBody.length());

        String s = new String(allMessage,0,allMessage.length);
        System.out.println(s);

        System.out.println("=========================");
        byte[] magics= new  byte[4];
        System.arraycopy(allMessage,0,magics,0,4);
        String magicr = new String(magics);

        System.out.println(magicr);
        byte versionr = bytesToByte(allMessage,4);
        System.out.println(versionr);
        byte msgTr = bytesToByte(allMessage,5);
        System.out.println(msgTr);

        byte[] ds= new  byte[1];
        System.arraycopy(allMessage,7,ds,0,1);
        String dsr = new String(ds);
        System.out.println(dsr);

        byte revTr = bytesToByte(allMessage,7);
        System.out.println(revTr);

        int timeStampParser = bytesToInt2(allMessage,8);
        System.out.println(timeStampParser);

        int reqr = bytesToInt2(allMessage,12);
        System.out.println(reqr);

        int datar = bytesToInt2(allMessage,16);
        System.out.println(datar);

        byte[] bodyParser= new  byte[datar];
        System.arraycopy(allMessage,20,bodyParser,0,datar);
        String bodyParserr = new String(bodyParser);
        System.out.println(bodyParserr);

        /*byte[] bodyParser = new  byte[bodyLenParser];
        System.arraycopy(allMessage,9,bodyParser,0,bodyLenParser);
        String bodyParserStr = new String(bodyParser);*/



    }
}
