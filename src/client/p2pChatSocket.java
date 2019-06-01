package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mr on 2019/4/29.
 */
public class p2pChatSocket extends Thread {
    DialogBox dia;
    private int port;
    DatagramSocket datagramSocket;

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public DialogBox getDia() {
        return dia;
    }

    public void setDia(DialogBox dia) {
        this.dia = dia;
    }

    public void run() {

        try {
            while(true){
                System.out.println("in p2pchatsocket: before recieve");
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, 256);
                //线程等待
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                datagramSocket.receive(packet);
                System.out.println("in p2pchatsocket: after recieve");

                int length=0;
                int i=0;
                for(; i<256; i++){
                    if(buf[i]!='\0'){
                        length++;
                    }else{
                        break;
                    }
                }
                byte[] buf1 = new byte[length];
                for(i=0; i<length; i++){
                    buf1[i] = buf[i];
                }

                String line = new String(buf1);
                System.out.println(length);

                String[] isImage = line.split("\\.");

                if(isImage.length==2 && isImage[1].equals("jpg")){
                    dia.showImage(line);
                }else{
                    dia.showMessage(line);
                    System.out.println("in p2pchatsocket " + dia.getTitle1());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

//    public static void main(String[] args) {
//
//    }
}
