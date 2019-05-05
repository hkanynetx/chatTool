package client;

import server.ChatManager;
import server.MyServer;

import java.awt.*;

/**
 * Created by Mr on 2019/4/27.
 */
public class Main {
    public static Index idx;
    public static Uwindows userWindows;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //加载服务器界面
                    idx = new Index();
                    System.out.println("in client main:" + ChatManager.getChatManager().vector.size());


                    //从登陆界面获得用户名，ip，port，
                    String ip = idx.getIp();
                    String username = idx.getUsername();
//                    int port = idx.getPort();

                    //加载监听器
//                    serverListener = new ServerListener();

                    //绑定界面与监听器
//                    serverWindows.getMyServer().setServerListener(serverListener);
//                    serverListener.getServerListener().setServer(serverWindows);

                    //界面操作，组件响应

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
