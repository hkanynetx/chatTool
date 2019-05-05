package server;
/**
 * Created by Mr on 2019/4/23.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

/*每一个连上的客户端，服务器都有一个线程为之服务*/
public class ChatSocket extends Thread {
    String username;
    Socket socket;
    private ChatSocket(){}
    //单例化该类
    private static final ChatSocket cs = new ChatSocket();
    public static ChatSocket getChatSocket(){
        return cs;
    }

    MyServer server;
    //传参接口server
    public void setServer(MyServer server) {
        this.server = server;
    }
    public ChatSocket(Socket s) {
        this.socket = s;
    }
    boolean flag = true;

    //单播
    public void out(String out) {
        try {
            this.socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //广播
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String line;

            //读取用户名
            String name;
            while (flag) {
                name = br.readLine();
                username = name;
                String sendMessage = new Date() + " " + name + " 进入聊天室";
                this.server.showMessage(sendMessage);
                ChatManager.getChatManager().publish(this, sendMessage);
                this.server.addUsername(name);
                flag = false;
            }

            //读取
            // TODO 如何能一直保证br.readLine()!=null
            /*
            * Q: 如何能一直保证br.readLine()!=null
            * A: client 与 server 输入流交互执行,while循环等待
            * */

            while ((line = br.readLine())!=null) {
                String[] isImage = line.split("\\.");
                //当客户端向服务器端请求在线用户名list时：
                if(line.equals("onlineUsernameList")){
//                    System.out.println("向客户端发送在线用户列表：");
                    ChatManager.getChatManager().respond(this, "onlineUsernameList");
                    String[] usernamelist = new String[200];
                    int count = this.server.userOnlineList0.size();
                    String a = String.valueOf(count);
//                    System.out.println("in chatsocket：用户数量"+count);
                    ChatManager.getChatManager().respond(this, a);
//                    System.out.println("after");

                    int i=0;
                    for(; i<this.server.userOnlineList0.size(); i++){
                        usernamelist[i] = this.server.userOnlineList0.get(i);
                        ChatManager.getChatManager().respond(this, usernamelist[i]);
                    }
                }else if(line.equals("getFriendSocket")){
                    ChatManager.getChatManager().respond(this, "getFriendSocket");
                    String friend_name = br.readLine();
                    int count = ChatManager.getChatManager().vector.size();
                    int i=0;
                    for(; i<count; i++){
                        if(friend_name.equals(ChatManager.getChatManager().vector.get(i).username)){
                            String ip = ChatManager.getChatManager().vector.get(i).socket.getRemoteSocketAddress().toString();
                            int port = ChatManager.getChatManager().vector.get(i).socket.getPort();

                            StringBuffer stringBuffer = new StringBuffer("");
                            for (int index = 0; index < ip.length(); index++) {
                                if (ip.charAt(index) != '/') {
                                    stringBuffer.append(ip.charAt(index));
                                }
                            }

//                            String[] info1 = ip.split(":");
//                            char[] a = info1[0].toCharArray();
//                            int length = a.length;
//                            int index=0;
//                            for(; index<length-1; index++){
//                                a[index] = a[index+1];
//                            }
//                            a[index-1] = '\0';
//                            info1[0] = a.toString();
//                            String info = info1[0]+","+info1[1];
//                            System.out.println("in chatSocket: ip "+info1[0]);
//                            System.out.println("in chatSocket: port"+info1[1]);
//                            System.out.println("in chatSocket: info"+info);
//                            ChatManager.getChatManager().respond(this, info);
                            ChatManager.getChatManager().respond(this, stringBuffer.toString());
                            break;
                        }
                    }
                } else if(isImage.length==2 && isImage[1].equals("jpg")){
                    this.server.showImage(line);
                    ChatManager.getChatManager().publish(this, line);
                }else{
                    this.server.showMessage(line);
                    ChatManager.getChatManager().publish(this, line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MyServer getServer() {
        return server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public static void main(String[] args){
//
//        String ip = "/127.0.0.1:50838";
//
//        StringBuffer stringBuffer = new StringBuffer("");
//        for (int i = 0; i < ip.length(); i++) {
//            if (ip.charAt(i) != '/') {
//                stringBuffer.append(ip.charAt(i));
//            }
//        }
//
//        System.out.println("in chatSocket: ip "+stringBuffer.toString());
//
//    }
}

