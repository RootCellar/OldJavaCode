import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Server
{
    static ArrayList<readThread> r = new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static String in="";
    public static void main(String args[]) throws Exception{
        System.out.println("Starting server...");
        boolean done=false;
        ServerSocket server= new ServerSocket(Integer.parseInt(args[0]));
        server.setSoTimeout(100);
        inThread w = new inThread();
        Thread w2 = new Thread(w);
        w2.start();
        Socket client;
        readThread t;
        Thread t2;
        System.out.println("Server started.");
        while(!done) {
            for(int i=0; i<r.size();i++) {
                r.get(i).setNumber(i);
            }
            client = null;
            try{
                client = server.accept();
            }catch(Exception e) {

            }
            Thread.sleep(100);
            if(client!=null) {
                System.out.println("Connected to "+client.getRemoteSocketAddress());
                System.out.println("Setting up threads...");
                t=new readThread();
                t2=new Thread(t);
                t2.start();
                t.setSocket(client);
                if(r.size()<50) {
                    r.add(t);
                    r2.add(t2);
                    t.setNumber(r.size()-1);
                }
                else {
                    sendTo("Sorry, too many players!",client);
                    sendTo("You will now be kicked.",client);
                    t2.stop();
                    client.close();
                }
            }
            if(in.equals("/count")) {
                System.out.println(r.size());
                in="";
            }
            else if(in.length()>0) {
                for(int i=0; i<r.size(); i++) {
                    sendTo("Server: "+in,r.get(i).getSocket());
                }
                in="";
            }
        }
    }

    public static String Display(String c[]) {
        /**
        System.out.printf("%s | %s | %s\n",c[0],c[1],c[2]);
        System.out.printf("------------\n");
        System.out.printf("%s | %s | %s\n",c[3],c[4],c[5]);
        System.out.printf("------------\n");
        System.out.printf("%s | %s | %s\n\n\n",c[6],c[7],c[8]);
         */
        return c[0]+" | "+c[1]+" | "+c[2]+"\n"
        +"--------\n"
        +c[3]+" | "+c[4]+" | "+c[5]+"\n"
        +"--------\n"
        +c[6]+" | "+c[7]+" | "+c[8]+"\n";
    }

    public static void deleteThread(int i) {
        r.remove(i);
        r2.remove(i);
        for(int w=0; w<r.size();w++) {
            r.get(w).setNumber(w);
        }
    }

    public static void sendTo(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

    public static void setin(String n) {
        in=n;
    }
}

