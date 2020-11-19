import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[]) {
        try{
            System.out.println("Trying to connect to the server...");
            Socket server = new Socket("localhost",25565);
            System.out.println("Trying to talk to the server...");
            new DataOutputStream(server.getOutputStream()).writeUTF("Hello! I have connected.");
            System.out.println("Reply: "+ new DataInputStream(server.getInputStream()).readUTF());
            System.out.println("Closing connection...");
            server.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}