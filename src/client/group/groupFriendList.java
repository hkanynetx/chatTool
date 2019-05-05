package client.group;

import client.FileOpt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Created by Mr on 2019/5/3.
 */
public class groupFriendList extends Frame {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel groupLabel;
    private static JTextField groupText;

    private static JLabel portLabel;
    private static JTextField portText;

    private static JButton certainButton;
    private static JButton cancelButton;
    private static String username;
    public  static String groupInfo;
    public  static JComboBox jcb;

    public static String getGroupInfo() {
        return groupInfo;
    }

    public static void setGroupInfo(String groupInfo) {
        groupFriendList.groupInfo = groupInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public groupFriendList(String myname){
        frame = new JFrame("Friend List");
        frame.setBounds(435, 225, 350, 230);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, myname);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, String myname) {
        panel.setLayout(null);

        JLabel chooseFriend = new JLabel("选择好友：");
        chooseFriend.setBounds(32,40,80,30);
        panel.add(chooseFriend);

        String[] friendList = new String[20];
        String fileName = "D:\\homework2\\计算机网络\\client\\data\\friendsList.txt";
        FileOpt file = new FileOpt();
        int friends_number = file.friend_file(fileName, friendList, myname);
        System.out.println("in groupFriendList: username : " + myname);
        String[] friendlist1 = new String[friends_number];
        int j=0;
        for(; j<friends_number; j++){
            friendlist1[j] = friendList[j];
            System.out.println(friendlist1[j]);
        }

        jcb = new JComboBox(friendlist1);
        jcb.setBounds(122,40,180,30);
        panel.add(jcb);

        certainButton = new JButton("确定");
        certainButton.setBounds(32, 130, 130, 30);
        panel.add(certainButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(172, 130, 130, 30);
        panel.add(cancelButton);

        certainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] array = groupInfo.split(":");
                String group_name = array[0];
                String group_port = array[1];

                String friendName = jcb.getSelectedItem().toString();
                System.out.println(friendName);

                FileOpt file = new FileOpt();
                String fileName = "D:\\homework2\\计算机网络\\client\\data\\groupInfo.txt";
                try {
                    file.writeNewMember(fileName, group_name, friendName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //创建成功！
                JOptionPane.showMessageDialog(null, "添加群成员成功！");
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
//
//    public static void main(String[] args){
//        groupFriendList cgb = new groupFriendList();
//        String friendName = jcb.getSelectedItem().toString();
//        System.out.println(friendName);
//    }
}

