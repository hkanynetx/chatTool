package client.group;
/**
 * Created by Mr on 2019/5/3.
 */
import server.ChatSocket;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class groupChatListener extends Thread {
    int port;
    String ip;
    groupClientManager gcm;
    ServerSocket serverSocket;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public groupClientManager getGcm() {
        return gcm;
    }

    public void setGcm(groupClientManager gcm) {
        this.gcm = gcm;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void run() {

        try {
            System.out.println("in groupChatListener :" + port);
            serverSocket = new ServerSocket(port);
            groupClientManager gcm = new groupClientManager();

            while (true) {
                Socket socket = serverSocket.accept();
                JOptionPane.showMessageDialog(null, "多人p2p聊天室建立");

                //创建线程
                groupServerSocket gss = new groupServerSocket(socket);
                gss.start();

                //加入管理队列
                gcm.add(gss);

                //为每一个线程传递manager参数
                gss.setGcm(gcm);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

