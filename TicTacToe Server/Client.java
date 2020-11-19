import java.net.*;
import java.io.*;

public class Client {
    static Socket server;
    public static void main(String args[]) {
        sendThread t = new sendThread();
        Thread t2=new Thread(t);
        t2.start();
        try{
            System.out.println("Trying to connect to the server...");
            server = new Socket(args[0],Integer.parseInt(args[1]));
            System.out.println("Connected.");
            String in="";
            while(true) {
                in=new DataInputStream(server.getInputStream()).readUTF();
                System.out.println(in);
                //System.out.println("DEBUG: "+in.getBytes());
            }
        }catch(IOException e) {
            System.out.println("\n\nCan't connect to server");
            t2.stop();
        }
    }

    public static void send(String m) {
        try{
            new DataOutputStream(server.getOutputStream()).writeUTF(m);
        }catch(IOException e) {
            System.out.println("Failed to send message to server");
        }
    }
}