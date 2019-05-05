//import server.ChatSocket;
//
//import java.util.Vector;
//
///**
// * Created by Mr on 2019/4/29.
// */
//public class ConManager {
//
//    Vector<ChatSocket> vector = new Vector<ChatSocket>();
//    /*增加ChatSocket 实例到vector中*/
//    public void add(ChatSocket cs){
//        vector.add(cs);
//    }
//
//    /*发布消息给其他客户端
//     *ChatSocket cs： 调用publish的线程
//     *msg：要发送的信息 */
//    public void publish(ChatSocket cs, String msg){
//        for (int i = 0; i < vector.size(); i++) {
//            ChatSocket csTemp = vector.get(i);
//            if (!cs.equals(csTemp)) {
//                csTemp.out(msg+"\n");//不用发送给自己。
//            }
//        }
//    }
//
//    public void send(ChatSocket cs, String msg){
//        //只发送给单独一个客户端
//        for (int i = 0; i < vector.size(); i++) {
//            ChatSocket csTemp = vector.get(i);
//            if (!cs.equals(csTemp)) {
//                csTemp.out(msg+"\n");//不用发送给自己。
//            }
//        }
//    }
//}
