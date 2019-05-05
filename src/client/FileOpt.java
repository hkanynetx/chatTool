package client;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Mr on 2019/4/22.
 */
public class FileOpt {
    private String fileName;

    public FileOpt(){}

    public int readFile(String fileName, String[] username){
        File file = new File(fileName);
        Reader reader = null;
        int count1 = 0;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int i = 0;
            int j = 0;
            int tempchar;
            ArrayList<Character> str = new ArrayList<>();
            while ((tempchar = reader.read()) != -1) {
                if (((char) tempchar) != '\r'  && ((char) tempchar) != '\n') {
                    str.add(i,(char)tempchar);
                    i++;
                }else if(((char) tempchar) == '\n'){
                    char[] array = new char[str.size()];
                    int count;
                    for(count = 0; count < str.size(); count++){
                        array[count] = str.get(count);
                    }
                    String s = new String(array);
                    username[j] = s;
                    j++;
                    i = 0;
                    str = new ArrayList<>();
                }
            }
            count1 = j;
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count1;//记录数量
    }

    /*
    * 根据自己的用户名
    * 获得已加入群名称(端口号)list
    * */
    public int readGroupInfo(String fileName, String[] groupInfo, String username){
        ArrayList<String> str = new ArrayList<>();
        String[] allGroupInfo = new String[200];
        int couple = readFile(fileName, allGroupInfo);
        int i=0;
        String[][] array = new String[100][3];
        for(; i<couple; i++){
            array[i] = allGroupInfo[i].split(",");
            /*
            * array[i][0] = 群名称
            * array[i][1] = 群端口号
            * array[i][2] = 群成员用户名list
            * */
            String[][] nameArray = new String[100][20];
            nameArray[i] = array[i][2].split(";");
            int index = 0;
            int length = array[i][2].split(";").length;
            for(; index<length; index++){
                if(nameArray[i][index].equals(username)){
                    String a = array[i][0] + ":" + array[i][1] ;
                    str.add(0,a);
                    break;
                }
            }
        }
        for(i=0; i<str.size(); i++){
            groupInfo[i] = str.get(i);
        }
        return i;
    }


    public int readChatHistory(String fileName, String[] chatHistory, String myname, String fname){
        ArrayList<String> str = new ArrayList<>();
        String[] history = new String[200];
        int couple = readFile(fileName, history);
        int i=0;
        int j=0;
        String[][] array = new String[100][3];
        for(; i<couple; i++){
            array[i] = history[i].split(",");
            if((array[i][0].equals(myname) && array[i][1].equals(fname)) || (array[i][1].equals(myname) && array[i][0].equals(fname))){
                str.add(j,history[i]);
                j++;
            }
        }
        for(i=0; i<str.size(); i++){
            chatHistory[i] = str.get(i);
        }
        return j;
    }

    public int readChatHistory(String fileName, String[] chatHistory, String groupName){
        ArrayList<String> str = new ArrayList<>();
        String[] history = new String[200];
        int couple = readFile(fileName, history);
        int i=0;
        int j=0;
        String[][] array = new String[100][2];
        for(; i<couple; i++){
            array[i] = history[i].split(",");
            if(array[i][0].equals(groupName)){
                str.add(j,array[i][1]);
                j++;
            }
        }
        for(i=0; i<str.size(); i++){
            chatHistory[i] = str.get(i);
        }
        return j;
    }


    public int friend_file(String fileName, String[] username, String myname){
        int couple = readFile(fileName, username);
        int i=0;
        int friends_number = 0;
        ArrayList<String> str = new ArrayList<>();
        String[][] name = new String[20][2];
        String[] names = new String[20];
        for(; i<couple; i++){
            name[i] = username[i].split(",");
            if(name[i][0].equals(myname)){
                names[friends_number] = name[i][1];
                friends_number ++;
            }else if(name[i][1].equals(myname)){
                names[friends_number] = name[i][0];
                friends_number ++;
            }
        }
        for(i=0; i<friends_number; i++){
            username[i] = names[i];
        }
        return friends_number;
    }


    public int writeFile(String fileName, String username, String password){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("no file exists");
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file, true);
//                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                writer.write(username + "," + password + "\r\n");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void writeFile(String fileName, String[] username) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        BufferedWriter out = new BufferedWriter(writer);
        int count = username.length;
        int i=0;
        for(; i<count; i++){
            out.write(username[i] + "\r\n");
        }
        out.close();
    }
    /*
      * 发送方用户名：title
      * 接收方用户名：dioname
      * 消息内容：userText.getText()
      * */
    public int writeChatHistory(String fileName, String sendName, String recieveName, String message){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("no file exists");
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file, true);){
                writer.write(sendName + "," + recieveName + "," + message + "\r\n");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /*
    * 将群组信息写入文件:
    * 群名称 groupName
    * 群端口 groupPort
    * 管理队列中群成员usernameList
    * */
    public int writeGroupFile(String fileName, String groupName, String groupPort, String[] usernameList){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("no file exists");
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file, true);
            ) {
                String userList = null;
                int i=0;
                for(; i<usernameList.length-1; i++){
                    if(userList==null){
                        userList = usernameList[i] + ';';
                    }else{
                        userList += usernameList[i] + ';';
                    }
                }
                if(userList==null){
                    userList = usernameList[i] + ';';
                }else{
                    userList += usernameList[i] + ';';
                }

                writer.write(groupName + "," + groupPort + "," + userList + "\r\n");

                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int writeGroupFile(String fileName, String groupName, String groupPort, String usernameList){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("no file exists");
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file, true);
            ) {

                writer.write(groupName + "," + groupPort + "," + usernameList + "\r\n");

                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
    * 群聊记录：
    * 群名: groupName
    * 群成员: memberName
    * 内容: message
    * */
    public int writeGroupChatHistory(String fileName, String groupName, String memberName, String message){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("no file exists");
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file, true);
            ) {
                writer.write(groupName + "," + memberName + "," + message + "\r\n");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
    * 根据群名找到对应的记录
    * 读取已有的群成员
    * 追加一个群成员
    * 写回文件
    * */
    public void writeNewMember(String fileName, String group_name, String new_member) throws IOException {
        String[] groupInfo = new String[20];
        int count = readFile(fileName, groupInfo);
        String[][] array = new String[count][3];
        /*
        * array[0]:群名称
        * array[1]:群端口号
        * array[2]:群成员;
        * */
        int i=0;
        for(; i<count; i++){
            array[i] = groupInfo[i].split(",");
            if(array[i][0].equals(group_name)){
                //修改文件中的内容
                String members = array[i][2] + ";" + new_member;
                String newInfo = array[i][0] + "," + array[i][1];
                //删除第几行
                deleteFile(fileName, i+1);
                //添加新内容
                writeFile(fileName, newInfo, members);
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void deleteFile(String fileName, int row) throws IOException {
        String[] allInfo = new String[200];
        //读取原来文件的内容
        int count = readFile(fileName, allInfo);
        ArrayList<String> str = new ArrayList<>();
        int i=0;
        for(; i<count; i++){
            if(i==row-1) continue;//删掉文件中的一行
            str.add(0,allInfo[i]);
        }
        //写回文件
        String[] newInfo = new String[count-1];
        for(i=0; i<count-1; i++){
            newInfo[i] = str.get(i);
        }
        writeFile(fileName, newInfo);
    }


//    public static void main(String[] args) throws IOException {
//        String fileName = "D:\\homework2\\计算机网络\\client\\data\\friendsList.txt";
//        FileOpt file = new FileOpt();
//        String[] friendlist = new String[20];
//        int i = file.friend_file(fileName, friendlist, "amy");
//        String[] friendlist1 = new String[i];
//        int j=0;
//        for(; j<i; j++){
//            friendlist1[j] = friendlist[j];
//            System.out.println(friendlist1[j]);
//        }
//    }
}
