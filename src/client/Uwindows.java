package client;
/**
 * Created by Mr on 2019/4/22.
 */
import client.group.CreatGroupBox;
import client.group.groupClientSocket;
import client.group.groupDialogBox;
import server.ChatManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;

/*
* 添加好友
* 与好友聊天
* 进入群聊（选择好友）
* 大厅（服务器显示的全部信息）
* 删除好友
* */

public class Uwindows extends JFrame{

    private JPanel contentPane;
    private static String username;
    public static int i;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Uwindows(String username) {
        this.setUsername(username);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(900, 10, 300, 700);

        JPanel panel = new JPanel();

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.add(panel);

        panel.setLayout(null);
        panel.setBounds(0,0,300,150);
        JLabel userLabel = new JLabel(username);
        userLabel.setBounds(80,20,140,40);
        panel.add(userLabel);

        JTextField searchText = new JTextField(20);
        searchText.setBackground(Color.LIGHT_GRAY);
        searchText.setText("  🔍 搜索");
        searchText.setBounds(0, 80, 300, 30);
        panel.add(searchText);

        JMenuBar menubar = new JMenuBar();
        menubar.setBounds(0, 110, 300, 40);
        JMenu messageFile = new JMenu("消息");
        messageFile.setBounds(0,0,100,40);
        JMenu friendFile = new JMenu("联系人");
        JMenuItem itemOne = new JMenuItem("好友");
        JMenuItem itemMore = new JMenuItem("群组");
        JMenu repostFile = new JMenu("动态");
        JMenu helpFile = new JMenu("帮助");

        menubar.add(messageFile);
        menubar.add(friendFile);
        friendFile.add(itemOne);
        friendFile.add(itemMore);
        menubar.add(repostFile);
        menubar.add(helpFile);
        panel.add(menubar);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0,150,300,550);
        contentPane.add(panel1);
        panel1.setVisible(true);


        //消息——服务器
        messageFile.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                panel1.removeAll();
                JButton server = new JButton("server");
                server.setName("server");
                server.setBounds(0, 0,300, 60);
                server.setBackground(Color.LIGHT_GRAY);
                panel1.add(server);
                server.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //与服务器的对话框
                        JButton temp = (JButton)e.getSource();
                        DialogBox dia = new DialogBox(temp.getName(), username);
                        dia.setVisible(true);

                        //显示历史记录
                        showServerHitory(dia, temp.getName());

                        //绑定后台操作函数
                        ClientManager.getClientManager().setWindow(dia);

                    }
                });
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });



        //好友
        itemOne.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {//只能检测到mousePressed事件
                super.mouseClicked(e);

                //读文件查看好友个数n并获取好友列表
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\friendsList.txt";
                FileOpt file = new FileOpt();
                int friends_number;

                String[] usernames  = new String[20];
                friends_number = file.friend_file(fileName, usernames, getUsername());

                JButton[] friends = new JButton[friends_number];
                panel1.removeAll();

                for(i=0; i<friends.length; i++)
                {
                    friends[i] = new JButton(usernames[i]);
                    friends[i].setName(usernames[i]);
                    friends[i].setBounds(0, 60*i,300, 60);
                    friends[i].setBackground(Color.LIGHT_GRAY);
                    panel1.add(friends[i]);

                    friends[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //如何能获取到button的文本信息/位置
                            JButton temp = (JButton)e.getSource();

                            //传参temp.getName() = username[i]
                            DialogBox dia = new DialogBox(temp.getName(), username);
                            dia.setVisible(true);

                            //显示历史记录
                            showHitory(dia, temp.getName());

                            /*
                            * （登录：建立自己的监听接口）
                            * 判断对方是否在线
                            * 在线：建立聊天室/连接成功
                            *       获取对方socket IP port 信息；主动连接；关闭窗口时释放连接；
                            * 离线：显示离线提示信息
                            * 用户状态传参给dia,点击send时有两种操作
                            * */
                            boolean online = IsOnline(temp.getName());
                            dia.setOnline(online);
                            if(online){

                                int port;
                                String ip;
                                String info = getSocket(temp.getName());

                                String[] info1 = info.split(":");
                                //服务器先发ip,再发port
                                ip = info1[0];
                                port = Integer.parseInt(info1[1]);

                                dia.setFriend_ip(ip);
                                dia.setFriend_port(port);

                                //UDP发送消息
                                DatagramSocket ds=null;
                                try {
                                    System.out.println("in uwindows: myport:"+ClientManager.getClientManager().socket.getLocalPort());
                                    ds = new DatagramSocket(ClientManager.getClientManager().socket.getLocalPort());
                                    dia.setDatagramSocket(ds);

                                } catch (SocketException e1) {
                                    e1.printStackTrace();
                                }

                                //接收消息线程
                                p2pChatSocket pcs = new p2pChatSocket();
                                pcs.setDia(dia);

                                int localport = ClientManager.getClientManager().socket.getLocalPort();
                                pcs.setPort(localport);
                                pcs.setDatagramSocket(ds);
                                pcs.start();

                            }else{
                                dia.showMessage("-----------------对方离线请留言---------------");
                            }

                        }
                    });
                }
            }
        });


        //群组
        itemMore.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {//只能检测到mousePressed事件
                super.mouseClicked(e);
                panel1.removeAll();
                JButton creatBtn = new JButton(" ＋ 创建群组  ");
                creatBtn.setName("group");
                creatBtn.setBounds(0, 0,300, 60);
                creatBtn.setBackground(Color.LIGHT_GRAY);
                panel1.add(creatBtn);

                /*
                * 读文件查看已加入的群聊
                * 新建按钮
                * 添加事件
                * */
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupInfo.txt";
                FileOpt file = new FileOpt();
                String[] groupInfo = new String[20];
                int j = file.readGroupInfo(fileName, groupInfo, username);
                JButton[] groupBtn = new JButton[j];
                for(int i=0; i<j; i++){
                    groupBtn[i] = new JButton(groupInfo[i]);
                    groupBtn[i].setName(groupInfo[i]);
                    groupBtn[i].setBounds(0, 60*i+60,300, 60);
                    groupBtn[i].setBackground(Color.LIGHT_GRAY);
                    panel1.add(groupBtn[i]);

                    groupBtn[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton temp = (JButton)e.getSource();

                            groupDialogBox gdb = new groupDialogBox(temp.getName(), username);

                            //显示历史纪录
                            String groupName = temp.getName().split(":")[0];
                            showGroupHitory(gdb, groupName);
                            gdb.setVisible(true);

                            groupClientSocket gcs = new groupClientSocket();
                            gcs.setGdb(gdb);
                            gdb.setGcs(gcs);

                            String[] array = temp.getName().split(":");
                            int port = Integer.parseInt(array[1]);
                            System.out.println("in Uwindows: 连接端口号为："+port);
                            gcs.connect("localhost", username, port);
                        }
                    });
                }

                /*
                * 弹出对话框：群名/群号
                * 确定：创建群组聊天室
                * 取消：
                * */
                creatBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CreatGroupBox cgb = new CreatGroupBox();
                        cgb.setUsername(username);
                    }
                });
            }
        });


        //动态
        repostFile.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String url = "https://user.qzone.qq.com/962671741/infocenter";
                clickToWeb(url);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });


        //帮助
        helpFile.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String url = "https://www.baidu.com/";
                clickToWeb(url);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

    }


    public void showHitory(DialogBox dia, String frname){
        String fileName = "D:\\homework2\\计算机网络\\client\\data\\chathistory.txt";
        FileOpt file = new FileOpt();
        String[] chatHistory  = new String[20];
        String myname = username;
        String fname = frname;
        int log = file.readChatHistory(fileName, chatHistory, myname, fname);
        int j=0;
        for(; j<log; j++){
            dia.showHistory(chatHistory[j]);
        }
        dia.showMessage("------------------历史记录-----------------");
    }


    public void showGroupHitory(groupDialogBox gdb, String groupName){
        //读历史纪录
        String[] chatHistory = new String[100];
        FileOpt file = new FileOpt();
        String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupChatHistory.txt";
        int count = file.readChatHistory(fileName, chatHistory, groupName);
        for(int i=0; i<count; i++){
            gdb.showMessage(chatHistory[i]);
        }
        gdb.showMessage("------------------历史记录-----------------");

    }

    public void showServerHitory(DialogBox dia, String groupName){
        //读历史纪录
        String[] chatHistory = new String[100];
        FileOpt file = new FileOpt();
        String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupChatHistory.txt";
        int count = file.readChatHistory(fileName, chatHistory, groupName);
        for(int i=0; i<count; i++){
            dia.showMessage(chatHistory[i]);
        }
        dia.showMessage("------------------历史记录-----------------");

    }


    public boolean IsOnline(String friend_name){
        //模拟TCP三次握手
        ClientManager.getClientManager().send("onlineUsernameList\n");

        //主线程等待
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        String[] namelist = ClientManager.getClientManager().recieve();
        int count = namelist.length - 1;

        int i=0;
//        for(; i<count; i++){
//            System.out.println("in dialogbox: 在线用户：" + namelist[i]);
//        }
//        i=0;
        //判断用户是否在线
        boolean online = false;
        for(; i<count; i++){
            if(namelist[i].equals(friend_name)){
                online = true;
                break;
            }
        }
        return online;
    }


    public String getSocket(String friend_name){
        //模拟TCP三次握手
        ClientManager.getClientManager().send("getFriendSocket\n");
        ClientManager.getClientManager().send(friend_name+'\n');

        //主线程等待
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        //服务器先发ip,再发port
        String info = ClientManager.getClientManager().getUserSocket();
        return info;
    }


    public void clickToWeb(String url){
        if(java.awt.Desktop.isDesktopSupported()){
            try {
                java.net.URI uri = java.net.URI.create(url);
                //获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    dp.browse(uri);
                }
            } catch (java.io.IOException e) {
                //无法获得默认浏览器
            }
        }
    }

//    public static void main(String[] args){
//        Uwindows users = new Uwindows("hello");
//        users.setVisible(true);
//    }

}
