//package client;
//
///**
// * Created by Mr on 2019/4/26.
// */
//
//import java.io.IOException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//
//public class ChatManager {
//
//    private ChatManager(){}
//    private static final ChatManager instance=new ChatManager();
//    public static ChatManager getChatManager(){
//        return instance;
//    }
//
//    DialogBox dialogbox;
//    Socket socket;
//    private String IP;
//    BufferedReader bReader;
//    PrintWriter pWriter;
//    String username;
//
//    public void setWindow(DialogBox dialogbox) {
//        this.dialogbox = dialogbox;
//        dialogbox.showMessage("文本框已经和chatManager绑定了");
//    }
//
//    public void connect(String ip, String username) {
//        this.IP = ip;
//        this.username = username;
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    socket = new Socket(IP, 8000);
//                    //输出流
//                    pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//                    //输入流
//                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                    String line = null;
//                    //如果读取数据为空
//                    while ((line = bReader.readLine())!=null) {
//                        dialogbox.showMessage("收到： "+line);
//                    }
//                    send(pWriter.toString());
//
//                    //读完数据之后要关闭
//                    pWriter.close();
//                    bReader.close();
//                    pWriter = null;
//                    bReader = null;
//
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//
//    //发送
//    public void send(String sendMsg){
//        if (pWriter != null) {
//            pWriter.write(sendMsg);
//            System.out.println("发送成功" + sendMsg + '\n');
//            pWriter.flush();
//        } else {
//            dialogbox.showMessage("当前链接已经中断...");
//        }
//    }
//}
//
//
