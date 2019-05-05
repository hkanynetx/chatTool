package client.group;

import client.DialogBox;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Mr on 2019/5/3.
 */
public class groupClientSocket {
    public groupClientSocket(){}

    groupDialogBox gdb;
    public Socket socket;
    private String ip;
    private int port;
    BufferedReader bReader;
    PrintWriter pWriter;
    String username;

    public groupDialogBox getGdb() {
        return gdb;
    }

    public void setGdb(groupDialogBox gdb) {
        this.gdb = gdb;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void connect(String ip, String username, int port) {
        this.ip = ip;
        this.username = username;
        new Thread(){
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    //输出流
                    pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    //输入流
                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = null;
                    //如果读取数据为空
                    while ((line = bReader.readLine())!=null) {
                        String[] isImage = line.split("\\.");
                        if(isImage.length==2 && isImage[1].equals("jpg")){
                            gdb.showImage(line);
                            continue;
                        }
                        gdb.showMessage(line);
                    }
                    if (pWriter != null) {
                        send(pWriter.toString());
                    }else {
                        gdb.showMessage("当前链接已经中断...");
                    }
                    //读完数据之后要关闭
                    pWriter.close();
                    bReader.close();
                    pWriter = null;
                    bReader = null;

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //发送
    public void send(String sendMsg){
        pWriter.write(sendMsg);
        System.out.println("发送成功" + sendMsg);
        pWriter.flush();
    }


//
//
//    public static void main(String[] args){
//        ClientManager cm = new ClientManager();
//        cm.connect("localhost", "test");
//
//    }

}



