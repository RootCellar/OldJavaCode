import java.net.*;
import java.io.*;
import java.util.ArrayList;
/**
 * readThread t = new readThread();
 * Thread t2=new Thread(t);
 * t2.start();
 */

public class Server
{
    static ArrayList<readThread> r=new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static String in="";
    public static void main(String args[]) {
        System.out.println("Starting Server...");
        inThread c = new inThread();
        Thread c2 = new Thread(c);
        c2.start();
        readThread t;
        Thread t2;
        ServerSocket server;
        try{
            server = new ServerSocket(Integer.parseInt(args[0]));
            server.setSoTimeout(100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Socket client=null;
        System.out.println("Server Started.");
        while(true) {
            try{
                client = null;
                client = server.accept();
                if(client!=null) {
                    System.out.println("Connected to "+client.getRemoteSocketAddress());
                    System.out.println("Setting up threads...");
                    send(client.getRemoteSocketAddress()+" connected.");
                    t=new readThread();
                    t2=new Thread(t);
                    t2.start();
                    t.setSocket(client);
                    r.add(t);
                    r2.add(t2);
                    t.setNumber(r.size()-1);
                    send("Welcome! use /help to get command list");
                }
            }catch(SocketTimeoutException s) {

            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
            if(in.equals("/list")) {
                in="";
                for(int i=0; i<r.size(); i++) {
                    System.out.println(i+". "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName());
                }
            }
            else if(in.length()>=6 && in.substring(0,6).equals("/kick ")) {
                try{
                    kick(Integer.parseInt(in.substring(6)));
                }catch(NumberFormatException e) {
                    System.out.println("Please type a number");
                }
                in="";
            }
            else if(in.equals("/kickall")) {
                send("Kicking everyone off of the server.");
                for(int i=r.size()-1; i>-1; i--) {
                    kick(i);
                }
                in="";
            }
            else if(!in.equals("")) {
                send(in);
                in="";
            }
        }
    }

    public static void kick(int i) {
        try{
            r2.get(i).stop();
            r.get(i).getSocket().close();
        }catch(IOException e) {

        }catch(IndexOutOfBoundsException e) {

        }
        send("Kicking "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName());
        deleteThread(i);
        System.out.println("Kicked "+i);
    }

    public static void deleteThread(int i) {
        r.remove(i);
        r2.remove(i);
        for(int w=0; w<r.size(); w++) {
            r.get(w).setNumber(w);
        }
    }

    public static void send(String message) {
        Socket client;
        System.out.println("Server: "+message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF("Server: "+message);
            }catch(IOException e) {

            }
        }
    }

    public static void send(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF("Server: "+message);
        }catch(IOException e) {

        }
    }

    public static void send(Socket from, String message, String m2) {
        Socket client;
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
            }catch(IOException e) {

            }
        }
    }

    public static void setin(String m) {
        in=m;
    }
}