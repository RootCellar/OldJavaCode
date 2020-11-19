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
            DataInputStream in = new DataInputStream(server.getInputStream());
            while(true) {
                try{
                    Object o = new ObjectInputStream(in).readObject();
                    System.out.println(o.toString());
                }catch(ClassNotFoundException e) {}
                catch(Exception e) {e.printStackTrace();}
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("\n\nCan't connect to server");
        }
    }

    public static void send(String m) {
        try{
            //new DataOutputStream(server.getOutputStream()).writeUTF(m);
            DataOutputStream o = new DataOutputStream(server.getOutputStream());
            new ObjectOutputStream(o).writeObject(new Packet("Message",m));
        }catch(IOException e) {
            System.out.println("Failed to send message to server");
        }
    }
}