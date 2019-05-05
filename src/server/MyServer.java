package server;

/**
 * Created by Mr on 2019/4/23.
 */
import client.FileOpt;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class MyServer extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JTextField txtXiaoXi;
    private JTextPane jta = new JTextPane();
    private int port;

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }


    ArrayList<String> userOnlineList0 = new ArrayList<>();
    public void addUsername(String username){
        System.out.println("in myserver:" + username);
        userOnlineList0.add(0, username);
    }

    ServerListener serverListener;
    private static final MyServer instance = new MyServer();
    public static MyServer getMyServer(){
        return instance;
    }
    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }

    ChatSocket chatSocket;
    public void setChatSocket(ChatSocket chatSocket) {
        this.chatSocket = chatSocket;
    }

    public MyServer() {
        JFrame frame = new JFrame();
        setName("my server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 470, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 426, 57);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("端口：");
        label.setBounds(30, 20, 40, 30);
        panel.add(label);

        textField = new JTextField();
        textField.setText("8000");
        textField.setBounds(80, 20, 50, 30);
        panel.add(textField);
        textField.setColumns(10);

        //设置端口
        this.setPort(Integer.parseInt(textField.getText()));
        port = Integer.parseInt(textField.getText());

        JButton button = new JButton("启动");
        button.setBounds(200, 20, 70, 30);
        panel.add(button);
        button.addActionListener(new buttonListener());

        JButton button_1 = new JButton("停止");
        button_1.addActionListener(new closeListener());
        button_1.setBounds(300, 20, 70, 30);
        panel.add(button_1);

        JScrollPane jsp = new JScrollPane(jta);
        jta.setBackground(Color.LIGHT_GRAY);
        jta.setEditable(false);
        jsp.setBounds(30, 69, 385,170);
        contentPane.add(jsp);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(30, 240, 428, 89);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        txtXiaoXi = new JTextField();
        txtXiaoXi.addActionListener(new sendListener());
        txtXiaoXi.setBounds(0, 10, 305, 25);
        panel_2.add(txtXiaoXi);
        txtXiaoXi.setColumns(10);

        JButton button_2 = new JButton("发送");
        button_2.setBounds(315, 10, 70, 25);
        panel_2.add(button_2);
        button_2.addActionListener(new sendListener());
    }

    //启动
    private class buttonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int port = Integer.parseInt(textField.getText());
            getMyServer().serverListener.setPort(port);
            getMyServer().serverListener.start();
            showMessage("服务器已启动  启动时间：" + new Date()+ '\n');
        }
    }

    //发送
    private class sendListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //发送数据给所有客户端
            Vector<ChatSocket> vector1 = ChatManager.getChatManager().vector;
            System.out.println("连接服务器的客户端个数："+ vector1.size());

            String sendMessage = "server  " + new Date() + " :" + txtXiaoXi.getText() +'\n';
            ChatManager.getChatManager().publish(sendMessage);
            showMessage(sendMessage);


            //聊天记录写入文件
            String historyMessage = "server  " + new Date() + " :" + txtXiaoXi.getText();
            FileOpt file = new FileOpt();
            String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupChatHistory.txt";
            file.writeFile(fileName, "server", historyMessage);

            txtXiaoXi.setText("");
        }
    }

    //断开连接
    private class closeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
//                getMyServer().serverListener.close();
                showMessage("连接已断开");
            } catch (Exception e2) {

            }
        }
    }

    //显示消息在文本框中
    public void showMessage(String message){
        //设置字体大小
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);

        //插入内容
        Document docs = jta.getDocument();//获得文本对象
        try {
            docs.insertString(docs.getLength(), message+'\n', attrset);//对文本进行追加
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    //插入图片
    public void showImage(String filePath){
        ImageIcon image = new ImageIcon(filePath);

        int width = image.getIconWidth();
        int height = image.getIconHeight();
        int neoheight = 200;
        int neowidth = width * neoheight / height;
        image.setImage(image.getImage().getScaledInstance(neowidth, neoheight, Image.SCALE_DEFAULT));
        showMessage("收到图片：\n");
        jta.setCaretPosition(jta.getDocument().getLength());
        jta.insertIcon(image);
        showMessage("\n");
    }

}
