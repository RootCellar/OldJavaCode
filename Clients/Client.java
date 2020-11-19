import java.net.*;
import java.io.*;

public class Client implements Runnable {
    Socket server;
    int number=-1;
    public void run() {
        while(number==-1) {
            try{
                Thread.sleep(1000);
            }catch(Exception e) {

            }
        }
        sendThread t = new sendThread();
        Thread t2=new Thread(t);
        t.setNum(number);

        while(true) {

            try{
                //System.out.println("Trying to connect to the server...");
                Thread.sleep(100);
                server = new Socket("localhost",10000);
                //System.out.println("Connected.");
                t.setS(server);
                String in="";
                t2.start();
                while(true) {
                    in=new DataInputStream(server.getInputStream()).readUTF();
                    //System.out.println(in);
                    //System.out.println("DEBUG: "+in.getBytes());
                }

            }catch(Exception e) {
                //e.printStackTrace();
                //System.out.println("\n\nCan't connect to server");
                //t2.stop();
            }
        }
    }

    public void setNumber(int n) {
        number=n;
    }

    public void send(String m) {
        try{
            new DataOutputStream(server.getOutputStream()).writeUTF(m);
        }catch(IOException e) {
            System.out.println("Failed to send message to server");
        }
    }
}