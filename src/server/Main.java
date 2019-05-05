package server;

import java.awt.*;

/**
 * Created by Mr on 2019/4/27.
 */
public class Main {
    public static MyServer serverWindows;
    public static ServerListener serverListener;

    public static void main(String[] args) {
        //加载服务器界面
        serverWindows = new MyServer();
        serverWindows.setVisible(true);

        serverListener = new ServerListener();

        //绑定界面、监听器
        serverWindows.getMyServer().setServerListener(serverListener);
        serverListener.getServerListener().setServer(serverWindows);

        //绑定界面、管理队列
        ChatManager.getChatManager().setWindow(serverWindows);


        //界面操作，组件响应

    }

}
