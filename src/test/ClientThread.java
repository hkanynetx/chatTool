//package client;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
///**
// * Created by Mr on 2019/4/22.
// */
//public class ClientThread  implements Runnable{
//    private Socket socket;
//    private DataOutputStream toServer;
//    private DataInputStream fromServer;
//
//    public void run(){
//        try {
//            socket.getPort();
//            fromServer = new DataInputStream(socket.getInputStream());
//            toServer = new DataOutputStream(socket.getOutputStream());
//            while(true){
//                String fromStr = fromServer.readUTF();
//                System.out.println("服务端发来消息：" +fromStr);
////                txtMessage.append("服务端发来消息：" +fromStr);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
