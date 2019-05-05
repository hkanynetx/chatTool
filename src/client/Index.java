package client;

/**
 * Created by Mr on 2019/4/22.
 */

import com.sun.deploy.util.SessionState;
import server.MyServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class Index extends Frame{
    private static Index idx;
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;

    private static JLabel serverLabel;
    private static JTextField serverText;
    private static JLabel portLabel;
    private static JTextField portText;

    private static JButton loginButton;
    private static JButton registerButton;



    public Index(){
        frame = new JFrame("Login");
        frame.setBounds(400,200,470, 350);
//        frame.setSize(470, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);


        frame.setVisible(true);
        }

        private static void placeComponents(JPanel panel) {


        panel.setLayout(null);


        userLabel = new JLabel("Username:");
        userLabel.setBounds(75,80,80,30);
        panel.add(userLabel);


        userText = new JTextField(20);
        userText.setBounds(160,80,230,30);
        panel.add(userText);


        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(75,120,80,30);
        panel.add(passwordLabel);


        passwordText = new JPasswordField(20);
        passwordText.setBounds(160,120,230,30);
        panel.add(passwordText);


        serverLabel = new JLabel("服务器IP");
        serverLabel.setBounds(75,160,80,30);
        panel.add(serverLabel);


        serverText = new JTextField(20);
        serverText.setBounds(160,160,80,30);
        panel.add(serverText);
        serverText.setText("localhost");


        portLabel = new JLabel("端口号：");
        portLabel.setBounds(250,160,80,30);
        panel.add(portLabel);


        portText = new JTextField(20);
        portText.setBounds(340,160,45,30);
        panel.add(portText);
        portText.setText("8000");


        // 创建登录按钮
        loginButton = new JButton("login");
        loginButton.setBounds(75, 220, 110, 30);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                FileOpt file = new FileOpt();
                //检查用户名密码是否符合/
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String [] userName = new String[20];
                int count = file.readFile("D:\\homework2\\计算机网络\\client\\data\\username.txt", userName);
                String [] userName1 = new String[count];
                for(int i=0; i<count; i++){
                    userName1[i] = userName[i];
                    System.out.println(userName1[i]);
                }
                boolean flag = user.check_user(userName1, username, password);
                if(flag){
                    //连接服务器，创建进程
                    int port = Integer.parseInt(portText.getText());
                    String ip = serverText.getText();

                    //建立线程，与服务器收发数据
                    ClientManager.getClientManager().connect(ip, username, port);

                    Uwindows uw = new Uwindows(userText.getText());
                    uw.setVisible(true);
                    idx.frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "用户不存在 或 密码错误");
                }
            }
        });


        // 创建注册按钮
        registerButton = new JButton("register");
        registerButton.setBounds(275, 220, 110, 30);
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将用户名密码写入文件中
                FileOpt file = new FileOpt();
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\username.txt";
                file.writeFile(fileName, username, password);
                //对话框提示注册成功，清空输入框中的内容
                JOptionPane.showMessageDialog(null, "                注册成功！", " ", JOptionPane.NO_OPTION);
                userText.setText("");
                passwordText.setText("");
            }
        });

    }

    public String getUsername(){
        return userText.getText();
    }

    public String getIp(){
        String ip = serverText.getText();
        return ip;
    }

//    public int getPort(){
//        int port = Integer.parseInt(portText.getText());
//        return port;
//    }

//    public static void main(String[] argvs){
//        FileOpt file = new FileOpt();
//        String [] userName = new String[20];
//        int count = file.readFile("D:\\homework2\\计算机网络\\client\\data\\username.txt", userName);
//        System.out.println(count);
//        String [] userName1 = new String[count];
//        for(int i=0; i<count; i++){
//            userName1[i] = userName[i];
//            System.out.println(userName1[i]);
//        }
//
//    }

}

