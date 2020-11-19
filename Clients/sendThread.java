import java.util.Scanner;
import java.net.*;
import java.io.*;
public class sendThread implements Runnable
{
    int number=-1;
    Socket Server;
    public void run() {
        Scanner in= new Scanner(System.in);
        send("/register");
        send("bot"+number);
        send("bot"+number);
        send("/login");
        send("bot"+number);
        send("bot"+number);
        send("/pick 0");
        while(true) {
            try{
                Thread.sleep(50);
                send("f");
            }catch(Exception e) {
                
            }
        }
    }
    
    public void send(String m) {
        try{
            new DataOutputStream(Server.getOutputStream()).writeUTF(m);
        }catch(IOException e) {
            System.out.println("Failed to send message to server");
        }
    }
    
    public void setNum(int n) {
        number=n;
    }
    
    public void setS(Socket c) {
        Server=c;
    }
}