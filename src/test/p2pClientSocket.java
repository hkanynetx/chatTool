//package client;
//
//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
///**
// * Created by Mr on 2019/5/1.
// */
//public class p2pClientSocket {
//    private String ip;
//    private String username;
//    private int port;
//    BufferedReader bReader;
//    PrintWriter pWriter;
//    Socket socket;
//    DialogBox dialogbox;
//
//    public void connect(String ip, String username, int port) {
//        this.ip = ip;
//        this.username = username;
//        this.port = port;
//
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    socket = new Socket(ip, port);
//                    //发送用户名
//                    System.out.println("in p2pclientmanager:" + username);
//                    //输出流
//                    pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//                    //输入流
//                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                    String line = null;
//                    //如果读取数据为空
//                    while ((line = bReader.readLine())!=null) {
//                        dialogbox.showMessage("收到： "+line);
//                    }
//                    if (pWriter != null) {
//                        send(pWriter.toString());
//                    }else {
//                        dialogbox.showMessage("当前链接已经中断...");
//                    }
//
//                    //读完数据之后要关闭
//                    pWriter.close();
//                    bReader.close();
//                    pWriter = null;
//                    bReader = null;
//
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    //发送
//    public void send(String sendMsg){
//        pWriter.write(sendMsg);
//        System.out.println("发送成功" + sendMsg + '\n');
//        pWriter.flush();
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//}
