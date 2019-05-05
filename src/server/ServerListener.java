package server;

/**
 * Created by Mr on 2019/4/23.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;

public class ServerListener extends Thread {
    private int port;
    private String message;

    public ServerListener(){}

    public ServerListener(int port){
        this.port = port;
    }

    MyServer server;
    private static final ServerListener instance = new ServerListener();
    //访问该单例的唯一接口
    public static ServerListener getServerListener(){
        return instance;
    }
    //为该单例传参server
    public void setServer(MyServer server) {
        this.server = server;
    }

    ChatSocket chatSocket;
    public ChatSocket getChatSocket() {
        return chatSocket;
    }
    public void setChatSocket(ChatSocket chatSocket) {
        this.chatSocket = chatSocket;
    }


    public void run() {
        try {
//            int port = ServerListener.getServerListener().server
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("in serverlistener :" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                ServerListener.getServerListener().server.showMessage("连接成功");
                JOptionPane.showMessageDialog(null, "客户端连到本机"+port+"端口");

                //创建线程
                ChatSocket cs = new ChatSocket(socket);
                cs.setServer(getServerListener().server);
                cs.start();

                //加入管理队列
                ChatManager.getChatManager().add(cs);
                System.out.println("in serverlistener:" + cs.username);
                ChatManager.getChatManager().addUsername(cs.username);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void close() throws IOException {
//        serverSocket.close();
//    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MyServer getServer() {
        return server;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

