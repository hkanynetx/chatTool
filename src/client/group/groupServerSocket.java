package client.group;

import server.ChatManager;
import server.MyServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by Mr on 2019/5/3.
 */

/*每一个连上的客户端，服务器都有一个线程为之服务*/
public class groupServerSocket extends Thread {
    String username;
    Socket socket;
    groupClientManager gcm;

    public groupClientManager getGcm() {
        return gcm;
    }

    public void setGcm(groupClientManager gcm) {
        this.gcm = gcm;
    }

    public groupServerSocket(Socket socket){
        this.socket = socket;
    }

    //单播
    public void out(String out) {
        try {
            this.socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //转发
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line;

            while ((line = br.readLine())!=null) {
                gcm.publish(this,line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args){
//    }
}

