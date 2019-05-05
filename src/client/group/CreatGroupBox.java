package client.group;

import client.ClientManager;
import client.FileOpt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mr on 2019/5/3.
 */
public class CreatGroupBox extends Frame {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel groupLabel;
    private static JTextField groupText;

    private static JLabel portLabel;
    private static JTextField portText;

    private static JButton certainButton;
    private static JButton cancelButton;
    private static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreatGroupBox(){
        frame = new JFrame("Creat New Group");
        frame.setBounds(435, 225, 350, 230);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        groupLabel = new JLabel("群组名称");
        groupLabel.setBounds(32,40,80,30);
        panel.add(groupLabel);


        groupText = new JTextField(20);
        groupText.setBounds(122,40,180,30);
        panel.add(groupText);

        portLabel = new JLabel("群组端口");
        portLabel.setBounds(32,80,80,30);
        panel.add(portLabel);


        portText = new JTextField(20);
        portText.setBounds(122,80,180,30);
        panel.add(portText);

        certainButton = new JButton("确定");
        certainButton.setBounds(32, 130, 130, 30);
        panel.add(certainButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(172, 130, 130, 30);
        panel.add(cancelButton);

        certainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String group_name = groupText.getText();
                int group_port = Integer.parseInt(portText.getText());

                //每个群组对应一个监听器
                groupChatListener gcl = new groupChatListener();
                gcl.setPort(group_port);
                gcl.start();

                //将群信息记入文档中
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupInfo.txt";
                FileOpt file = new FileOpt();
//                int length = gcm.getVector().size();
                String[] usernameList = new String[1];
                usernameList[0] = username;
                String groupName = groupText.getText();
                String groupNumber = portText.getText();
                file.writeGroupFile(fileName, groupName, groupNumber, usernameList[0]);

                //创建成功！
                JOptionPane.showMessageDialog(null, "群 " + groupName + "(" + groupNumber+ ") 创建成功");
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

//    public static void main(String[] args){
//        CreatGroupBox cgb = new CreatGroupBox();
//
//    }
}
