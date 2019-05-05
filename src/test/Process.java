//package client;
//import java.io.IOException;
//import java.net.Socket;
//
///**
// * Created by Mr on 2019/4/22.
// */
//public class Process {
//    private int port;
//    private String ip;
//    Socket socket;
//
//    public Process(){
//
//    }
//
//    public boolean creat_process(int port, String ip){
//        try {
////                        Socket s = new Socket(ip, port);
//            socket = new Socket(ip, port);
//            System.out.println("success!");
//            System.out.println(ip);
//            System.out.println(port);
//
//            //建立线程
//            ClientThread cltd = new ClientThread();
//            cltd.setSocket(socket);
//            Thread thread = new Thread(cltd);
//            thread.start();
//
//
//
////                        Client3_thread client3_thread = new Client3_thread();
////                        Thread thread = new Thread(client3_thread);
////                        thread.start();
//            //socket.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
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
//    public Socket getSocket() {
//        return socket;
//    }
//
//    public void setSocket(Socket socket) {
//        this.socket = socket;
//    }
//}
//
