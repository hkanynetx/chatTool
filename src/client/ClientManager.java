package client;

/**
 * Created by Mr on 2019/4/29.
 */

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientManager {

    private ClientManager(){}
    //单例化该类
    private static final ClientManager instance=new ClientManager();
    //该类单例的唯一访问接口
    public static ClientManager getClientManager(){
        return instance;
    }

    DialogBox dialogbox;
    public Socket socket;
    private String ip;
    private int port;
    BufferedReader bReader;
    PrintWriter pWriter;
    String username;
    boolean flag;
    boolean getUserSocket;
    ArrayList<String> userOnlineList = new ArrayList<>();
    ArrayList<String> userSocket = new ArrayList<>();


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isGetUserSocket() {
        return getUserSocket;
    }

    public void setGetUserSocket(boolean getUserSocket) {
        this.getUserSocket = getUserSocket;
    }

    //为该类单例传参dialog
    public void setWindow(DialogBox dialogbox) {
        this.dialogbox = dialogbox;
    }

    public void connect(String ip, String username, int port) {
        this.ip = ip;
        this.username = username;
        new Thread(){
            @Override
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    //发送用户名
                    System.out.println("in clientmanager:" + username);
                    //输出流
                    pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    send(username+'\n');
                    //输入流
                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    flag = true;
                    getUserSocket = true;

                    String line = null;
                    //如果读取数据为空
                    int i=0;
                    int count=0;

                    while ((line = bReader.readLine())!=null) {
                        String[] isImage = line.split("\\.");
                        if(isImage.length==2 && isImage[1].equals("jpg")){
                            dialogbox.showImage(line);
                            continue;
                        }
                        if(line.equals("onlineUsernameList")){
                            setFlag(false);
                            continue;
                        }else if(line.equals("getFriendSocket")){
                            setGetUserSocket(false);
                            continue;
                        }
                        if(!isFlag()){
                            //最后一个元素是数字
                            System.out.println("in clientmanager: 我可以显示了");
                            userOnlineList.add(0,line);
                            if(i==0){
                                count = Integer.parseInt(line);
                            }
                            i++;
                            if(i>count){
                                setFlag(true);
                            }
                        }
                        if(!isGetUserSocket()){
                            userSocket.add(0,line);
                            setGetUserSocket(true);
                        }
                        dialogbox.showMessage(line);
                    }

                    if (pWriter != null) {
                        send(pWriter.toString());
                    }else {
                        dialogbox.showMessage("当前链接已经中断...");
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

    public String[] recieve(){
        //最后一个元素是数字
        int n = userOnlineList.size();
        String[] namelist = new String[n];
        int i=0;
        for(; i<n; i++){
            namelist[i] = userOnlineList.get(i);
            System.out.println("in clientmanager recieve:"+namelist[i]);
        }
        return namelist;
    }

    public String getUserSocket(){
        String userSocket1 = userSocket.get(0);
        System.out.println("in clientmanager:" + userSocket1);
        return userSocket1;
    }

    //发送
    public void send(String sendMsg){
        pWriter.write(sendMsg);
        System.out.println("发送成功" + sendMsg);
        pWriter.flush();
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

//
//
//    public static void main(String[] args){
//        ClientManager cm = new ClientManager();
//        cm.connect("localhost", "test");
//
//    }

}



