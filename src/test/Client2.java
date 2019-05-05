//package test;
//
//import client.FileOpt;
//import client.User;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//import javax.swing.JLabel;
//import javax.swing.JTextField;
//import javax.swing.JButton;
//import javax.swing.JTextArea;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.awt.event.ActionEvent;
//import java.util.Scanner;
//
//public class Client2 extends JFrame {
//
//    private JPanel contentPane;
//    private JTextField textField_IP;
//    private JTextField textField_Port;
//    private JTextField txtXiaoxi;
//
//    //IO流
//    private DataOutputStream toServer;
//    private DataInputStream fromServer;
//    JTextArea txtMessage;
//    Socket socket;
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Client2 frame = new Client2();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    private static PrintWriter pw=null;
//    private static BufferedReader br=null;
//    private static Socket s;
//    static Scanner scanner=new Scanner(System.in);
//    /**
//     * Create the frame.
//     */
//    public Client2() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 450, 300);
//        contentPane = new JPanel();
//        contentPane.setToolTipText("Client");
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(12, 5, 415, 100);
//        contentPane.add(panel);
//        panel.setLayout(null);
//
//        JLabel lblip = new JLabel("服务器IP：");
//        lblip.setBounds(12, 12, 65, 15);
//        panel.add(lblip);
//
//        textField_IP = new JTextField();
//        textField_IP.setText("localhost");
//        textField_IP.setBounds(82, 10, 114, 19);
//        panel.add(textField_IP);
//        textField_IP.setColumns(10);
//
//        JLabel label = new JLabel("端口：");
//        label.setBounds(214, 12, 49, 15);
//        panel.add(label);
//
//        textField_Port = new JTextField();
//        textField_Port.setText("8000");
//        textField_Port.setBounds(265, 10, 114, 19);
//        panel.add(textField_Port);
//        textField_Port.setColumns(10);
//
//        JButton button = new JButton("连接");
//        button.setBounds(80, 50, 80, 20);
//        panel.add(button);
//
//        JButton button_1 = new JButton("断开");
//        button_1.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    toServer.close();
//                    fromServer.close();
//                    socket.close();
//                    txtMessage.append("连接已断开～～～");
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//
//            }
//        });
//        button_1.setBounds(270, 50, 80, 20);
//        panel.add(button_1);
//
//        JPanel panel_1 = new JPanel();
//        panel_1.setBounds(12, 100, 415, 118);
//        contentPane.add(panel_1);
//
//        txtMessage = new JTextArea();
//        txtMessage.setBackground(Color.LIGHT_GRAY);
//        txtMessage.setColumns(35);
//        txtMessage.setRows(10);
//        txtMessage.setTabSize(4);
//        //txtMessage.setText("message");
//        panel_1.add(txtMessage);
//
//        txtXiaoxi = new JTextField();
//        txtXiaoxi.addActionListener(new sendListener());
//        //txtXiaoxi.setText("xiaoxi");
//        txtXiaoxi.setBounds(20, 245, 300, 25);
//        contentPane.add(txtXiaoxi);
//        txtXiaoxi.setColumns(10);
//
//        JButton button_2 = new JButton("发送");
//        button_2.addActionListener(new sendListener());
//        button_2.setBounds(330, 245, 60, 25);
//        contentPane.add(button_2);
//
//
//        //连接
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                //获得ip  port
//                String ip = textField_IP.getText();
//                int port = Integer.parseInt(textField_Port.getText());
//
//                //用户登录服务器验证
//                User user = new User();
//                FileOpt file = new FileOpt();
//
//                String admin = textField_IP.getText();
//                String [] userName = new String[20];
//                file.readFile("D:\\homework2\\计算机网络\\client\\data\\username.txt", userName);
//                boolean flag = user.check_user(userName, admin);
//
//                //创建线程
//                if(flag){
//                    try {
////                        Socket s = new Socket(ip, port);
//                        socket = new Socket(ip, port);
//                        Client3_thread client3_thread = new Client3_thread();
//                        Thread thread = new Thread(client3_thread);
//                        thread.start();
//                        socket.close();
//
//                    } catch (IOException e1) {
//                        txtMessage.append(e1.toString() + '\n');
//                    }
//                }else{
//                    System.out.println("用户名不存在\n");
//                }
//            }
//        });
//    }
//
//
//    private class sendListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                //得到当前时间
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //  设置日期格式
//                String time = df.format(Calendar.getInstance().getTime());      //得到时间
//                toServer.writeUTF(txtXiaoxi.getText().trim() + '\n');    //向服务器发送消息
//                txtMessage.append( time + "发送的消息：" + txtXiaoxi.getText().trim() +'\n');
//                txtXiaoxi.setText("");        //输出后清空输入框
//            } catch (IOException e1) {
//                System.err.println(e1);
//            }
//        }
//    }
//
//    //消息收发线程
//    public class Client3_thread implements Runnable{
//        public void run(){
//            try {
//                fromServer = new DataInputStream(socket.getInputStream());
//                toServer = new DataOutputStream(socket.getOutputStream());
//                while(true){
//                    String fromStr = fromServer.readUTF();
//                    txtMessage.append("服务端发来消息：" +fromStr);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
//
////https://blog.csdn.net/m0_37947204/article/details/80489431