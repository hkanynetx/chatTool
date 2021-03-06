package client;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import server.ChatManager;
import server.ChatSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Created by Mr on 2019/4/22.
 */
public class DialogBox extends JFrame {
    private JPanel contentPane;
    private DataOutputStream toServer;
    private JTextPane jta;
    private JTextField userText;
    private static String title1;
    private boolean online;
    private String friend_ip;
    private int friend_port;
    DatagramSocket datagramSocket;
    p2pChatSocket pcs;

    public p2pChatSocket getPcs() {
        return pcs;
    }

    public void setPcs(p2pChatSocket pcs) {
        this.pcs = pcs;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public String getFriend_ip() {
        return friend_ip;
    }

    public void setFriend_ip(String friend_ip) {
        this.friend_ip = friend_ip;
    }

    public int getFriend_port() {
        return friend_port;
    }

    public void setFriend_port(int friend_port) {
        this.friend_port = friend_port;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    ChatManager chatManager;
    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

//    private DialogBox(){}
//    private static final DialogBox db = new DialogBox();
//    public static DialogBox DialogBox(){
//        return db;
//    }


    public DialogBox (String title, String username){

        setTitle(title);
        this.setTitle1(title);
        setName(username);

        setBounds(100, 100, 450, 530);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        jta = new JTextPane();
        JScrollPane jsp = new JScrollPane(jta);
        jta.setBackground(Color.LIGHT_GRAY);
        jta.setEditable(false);
        jsp.setBounds(20, 20, 390, 390);
        contentPane.add(jsp);

        JPanel panel = new JPanel();
        panel.setBounds(0, 420, 440, 30);
        panel.setLayout(null);
        panel.setVisible(true);


        JLabel userLabel = new JLabel(title);
        userLabel.setBounds(20,0,40,25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(50,0,220,25);
        panel.add(userText);
        contentPane.add(panel);

        JButton addPicBtn = new JButton("???");
        addPicBtn.setBounds(275, 0, 50, 25);
        panel.add(addPicBtn);
        addPicBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                int returnVal = chooser.showOpenDialog(addPicBtn);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                    String filepath = chooser.getSelectedFile().getAbsolutePath();
                    System.out.println("????????????" + filepath);
                    ImageIcon image = new ImageIcon(filepath);

                    int width = image.getIconWidth();
                    int height = image.getIconHeight();
                    int neoheight = 200;
                    int neowidth = width * neoheight / height;
                    image.setImage(image.getImage().getScaledInstance(neowidth, neoheight, Image.SCALE_DEFAULT));

                    String dioname = getTitle1();
                    if(dioname.equals("server")){
                        ClientManager.getClientManager().send(image.toString() + '\n');

                        FileOpt file = new FileOpt();
                        String historyMessage = username + " " + new Date()+ "  :" + image.toString();
                        String fileName = "D:\\homework2\\???????????????\\client\\data\\groupChatHistory.txt";
                        file.writeFile(fileName, "server", historyMessage);

                    }else{
                        if(online){
                            System.out.println("???????????????p2p??????????????????");

                            //????????????
                            byte[] buf = image.toString().getBytes();
                            int offset = 0;
                            int length = buf.length;
                            InetAddress address=null;
                            try {
                                address = InetAddress.getByName("localhost");
                            } catch (UnknownHostException e1) {
                                e1.printStackTrace();
                            }
                            DatagramPacket dp = new DatagramPacket(buf, offset, length, address, friend_port);
                            try {
                                datagramSocket.send(dp);
                                System.out.println("in uwindows: UDP ????????????to"+ friend_port + new String(buf));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }else{
                            //???????????????
                            FileOpt file = new FileOpt();
                            String fileName = "D:\\homework2\\???????????????\\client\\data\\chathistory.txt";
                            file.writeChatHistory(fileName, username, dioname, image.toString());
                            System.out.println("????????????" + image.toString());
                        }
                    }
                    showMessage("??????????????????\n");
                    jta.setCaretPosition(jta.getDocument().getLength());
                    jta.insertIcon(image);
                    showMessage("\n");
                }
            }
        });


        //????????????
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(330, 0, 80, 25);
        panel.add(sendButton);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dioname = getTitle1();
                String sendMessage =  username + " " + new Date()+ "  :" + userText.getText() + '\n';

                if(dioname == "server"){
                    ClientManager.getClientManager().send(sendMessage);

                    FileOpt file = new FileOpt();
                    String historyMessage =  username + " " + new Date()+ "  :" + userText.getText();
                    String fileName = "D:\\homework2\\???????????????\\client\\data\\groupChatHistory.txt";
                    file.writeFile(fileName, "server", historyMessage);

                }else{
                    if(online){
                        System.out.println("???????????????p2p??????????????????");
                        //????????????
                        byte[] buf = sendMessage.getBytes();
                        int offset = 0;
                        int length = buf.length;
                        InetAddress address=null;
                        try {
                            address = InetAddress.getByName("localhost");
                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        }
                        DatagramPacket dp = new DatagramPacket(buf, offset, length, address, friend_port);
                        try {
                            datagramSocket.send(dp);
                            System.out.println("in uwindows: UDP ????????????to"+ friend_port + new String(buf));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }else{
                        //???????????????
                        FileOpt file = new FileOpt();
                        String fileName = "D:\\homework2\\???????????????\\client\\data\\chathistory.txt";
                        file.writeChatHistory(fileName, username, dioname, userText.getText());
                        System.out.println("????????????" + userText.getText());
                    }
                }
                showMessage(sendMessage);
                userText.setText("");
            }
        });

    }

    public void showMessage(String message){
        //??????????????????
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);

        //????????????
        Document docs = jta.getDocument();//??????????????????
        try {
            docs.insertString(docs.getLength(), message+'\n', attrset);//?????????????????????
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    //????????????
    public void showImage(String filePath){
        ImageIcon image = new ImageIcon(filePath);

        int width = image.getIconWidth();
        int height = image.getIconHeight();
        int neoheight = 200;
        int neowidth = width * neoheight / height;
        image.setImage(image.getImage().getScaledInstance(neowidth, neoheight, Image.SCALE_DEFAULT));
        showMessage("???????????????\n");
        jta.setCaretPosition(jta.getDocument().getLength());//???????????????????????????????????????????????????
        jta.insertIcon(image);
        showMessage("\n");
    }


    public void showHistory(String history){
        String[] log = history.split(",");
        String neolog = log[0] + ":" + log[2];
        showMessage(neolog);
    }


    public String getMessage(){
        return userText.getText();
    }

//
//    public static void main(String[] args){
//
//
//    }

}

