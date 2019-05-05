package client.group;

import client.FileOpt;
import server.ChatManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.Date;
import javax.xml.crypto.Data;

public class groupDialogBox extends JFrame {
    private JPanel contentPane;
    private DataOutputStream toServer;
    private JTextPane jta;
    private JTextField userText;
    private static String title1;
    groupClientSocket gcs;

    public groupClientSocket getGcs() {
        return gcs;
    }

    public void setGcs(groupClientSocket gcs) {
        this.gcs = gcs;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
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

    public groupDialogBox (String title, String username){

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

        //邀请群成员
        JButton addMemberBtn = new JButton("😡");
        addMemberBtn.setBounds(20,0,20,25);
        panel.add(addMemberBtn);
        addMemberBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupFriendList gfl = new groupFriendList(username);
                gfl.setUsername(username);
                System.out.println("in groupDialogBox : username :" + username);
//                gfl.setVisible(true);
                gfl.setGroupInfo(title);
            }
        });

        userText = new JTextField(20);
        userText.setBounds(50,0,220,25);
        panel.add(userText);
        contentPane.add(panel);

        JButton addPicBtn = new JButton("＋");
        addPicBtn.setBounds(275, 0, 50, 25);
        panel.add(addPicBtn);
        addPicBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                int returnVal = chooser.showOpenDialog(addPicBtn);
                if(returnVal==JFileChooser.APPROVE_OPTION){
                    String filepath = chooser.getSelectedFile().getAbsolutePath();
                    System.out.println("文件目录" + filepath);
                    ImageIcon image = new ImageIcon(filepath);

                    int width = image.getIconWidth();
                    int height = image.getIconHeight();
                    int neoheight = 200;
                    int neowidth = width * neoheight / height;
                    image.setImage(image.getImage().getScaledInstance(neowidth, neoheight, Image.SCALE_DEFAULT));

                    gcs.send(image.toString() + '\n');

                    showMessage("发送的消息：\n");
                    jta.setCaretPosition(jta.getDocument().getLength());
                    jta.insertIcon(image);
                    showMessage("\n");

                    FileOpt file = new FileOpt();
                    String historyMessage = username + " " + new Date()+ "  :" + image.toString();
                    String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupChatHistory.txt";
                    file.writeFile(fileName, title.split(":")[0], historyMessage);

                }
            }
        });


        //发送消息
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(330, 0, 80, 25);
        panel.add(sendButton);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                String dioname = getTitle1();
                String sendMessage = username + " " + new Date()+ "  :" + userText.getText().trim() + '\n';
                gcs.send( sendMessage);
                showMessage(sendMessage);

                FileOpt file = new FileOpt();
                String historyMessage =  username + " " + new Date()+ "  :" + userText.getText();
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupChatHistory.txt";
                file.writeFile(fileName, title.split(":")[0], historyMessage);

                userText.setText("");
            }
        });

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
        jta.setCaretPosition(jta.getDocument().getLength());//加上这一行就可以控制图片插入的位置
        jta.insertIcon(image);
        showMessage("\n");
    }


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


    public void showHistory(String history){
        String[] log = history.split(",");
        String neolog = log[0] + ":" + log[2];
        showMessage(neolog);
    }


    public String getMessage(){
        return userText.getText();
    }


//    public static void main(String[] args){
//        groupDialogBox gdb = new groupDialogBox("test", "username");
//        gdb.setVisible(true);
//    }

}


