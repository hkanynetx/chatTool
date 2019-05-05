//package client;
//
//import server.ChatSocket;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * Created by Mr on 2019/4/29.
// */
//public class p2pChatListener extends Thread {
//    int port;
//    p2pClientManager pcm;
//    ServerSocket serverSocket;
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public void run() {
//
//        try {
//            System.out.println("in serverlistener :" + port);
//            serverSocket = new ServerSocket(port);
//
//            while (true) {
//                Socket socket = serverSocket.accept();
//                JOptionPane.showMessageDialog(null, "好友p2p聊天室建立");
//
//                //创建线程
//                p2pChatSocket pcs = new p2pChatSocket(socket);
//                pcs.start();
//
//                //加入管理队列
//                pcm = new p2pClientManager();
//                pcm.add(pcs);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
