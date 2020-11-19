import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Use "Server.methodname()" to access these methods
 * 
 */
public class Server
{
    static ArrayList<readThread> r=new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static String in="";
    static CommandListener commandy;
    static int cost=1000;
    /**
     * This method starts the other threads, and accepts connections.
     * @param args[] port number, as a string
     */
    public static void main(String args[]) {
        System.out.println("Starting Server...");
        System.out.println("Starting command listener...");
        commandy = new CommandListener();
        //Definition of commands here
        commandy.add(new samplecommand());
        //End commands
        if(args.length<2) {
            System.out.println("Can't start server, need port and land claim cost");
            try{
                throw new Exception("Need Arguments");
            }catch(Exception e) {
                e.printStackTrace();
            }
            return;
        }
        try{
            cost=Integer.parseInt(args[1]);
        }catch(Exception e) {
            System.out.println("Argument 1 is not an integer, using default value 1000 instead");
            e.printStackTrace();
        }
        checker w = new checker();
        Thread w2 = new Thread(w);
        w2.start();
        inThread c = new inThread();
        Thread c2 = new Thread(c);
        c2.start();
        readThread t;
        Thread t2;
        ServerSocket server;
        ServerSocket server2;
        boolean done=false;
        int x=-1;
        int y=-1;
        try{
            server = new ServerSocket(Integer.parseInt(args[0]));
            server2 = new ServerSocket(Integer.parseInt(args[0])+1);
            server.setSoTimeout(100);
            server2.setSoTimeout(100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Socket client=null;
        Socket client2=null;
        System.out.println("Server Started.");
        System.out.println("Use /help to get command list.");
        System.out.println("Port: "+Integer.parseInt(args[0]));
        while(!done) {
            try{
                try{
                    client = null;
                    client2 = null;
                    client = server.accept();
                    client2 = server2.accept();
                    try{
                        Thread.sleep(100);
                    }catch(Exception d) {
                    }
                    if(client!=null && client2!=null) {
                        System.out.println("Connected to "+client.getRemoteSocketAddress());
                        System.out.println("Setting up threads...");
                        send(client.getRemoteSocketAddress()+" connected.");
                        t=new readThread();
                        t2=new Thread(t);
                        t2.start();
                        t.setSocket(client);
                        t.setSocket2(client2);
                        if(r.size()<50) {
                            r.add(t);
                            r2.add(t2);
                            t.setNumber(r.size()-1);
                            while(r.get(r.size()-1).getUser()==null) {
                                Thread.sleep(100);
                            }
                            catchUp(r.size()-1);
                            //System.out.println("Set up!");
                        }
                        else {
                            sendTo("Sorry, too many players!",client);
                            sendTo("You will now be kicked.",client);
                            send(client.getRemoteSocketAddress()+" disconnected (too many players)");
                            t2.stop();
                            client.close();
                        }
                    }
                }catch(SocketTimeoutException s) {

                }catch(IOException e) {
                    e.printStackTrace();
                }
                if(in.equals("/stop")) {
                    done=true;
                    send("Stopping server...");
                }
                else if(in.equals("/help")) {
                    System.out.println("/Stop - Stop the server");
                    System.out.println("/help - view command list");
                    System.out.println("/list - view player list. \nIt shows number, ip, name(if set), Tank, and some debug info");
                    System.out.println("/kick <number> - use /list to \nfind the number of the player, than use /kick (number) to disconnect them");
                    System.out.println("/kickall - kicks every player \noff of the server");
                    in="";
                }
                else if(in.equals("/list")) {
                    in="";
                    for(int i=0; i<r.size(); i++) {
                        System.out.println(i+". "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName()+" "+r.get(i).getNumber());
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
                else if(in.length()>1 && in.substring(0,1).equals("/")) {
                    System.out.println("Invalid command. Use /help to get command list");
                    in="";
                }
                else if(!in.equals("")) {
                    send(in);
                    in="";
                }
            }catch(Exception e) {
                send("Server error");
                e.printStackTrace();
                in="";
            }
        }
        w2.stop();
        c2.stop();
        for(int i=0; i<r.size(); i++) {
            r2.get(i).stop();
            deleteThread(i);
        }
        try{
            server.close();
        }catch(Exception e) {

        }
    }

    /**
     * Returns all of the threads, which contain info for the logged in players
     */
    public static ArrayList<readThread> getThreads() {
        return r;
    }

    /**
     * Used by readThread to get the command listener
     */
    public static CommandListener getListener() {
        return commandy;
    }

    /**
     * Returns the remainder of the string given after the first space
     */
    public static String subUpToSpace(String s) {
        String s2="";
        int x=0;
        if(s==null) {
            return "";
        }
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return "";
            }
        }
        return s.substring(x,s.length()); 
    }

    /**
     * If b is true, this method returns s up to the first space.
     * If b is false, this method returns s up to and including the first space
     */
    public static String upToSpace(String s, boolean b) {
        String s2="";
        int x=0;
        if(s==null) {
            return "";
        }
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return s;
            }
        }
        if(b==true) {
            return s.substring(0,x-1);
        }
        return s.substring(0,x);
    }
    
    public static String[] getArgs(String command) {
        command=subUpToSpace(command);
        int x=0;
        for(int i=0; i<command.length(); i++) {
            if(command.substring(i,i+1).equals(" ")) {
                x++;
            }
        }
        x++;
        String[] ret = new String[x];
        for(int i=0; i<x; i++) {
            ret[i]=upToSpace(command,true);
            command=subUpToSpace(command);
        }
        return ret;
    }

    /**
     * Kicks a player based on their number
     */
    public static void kick(int i) {
        try{
            r2.get(i).stop();
            r.get(i).getSocket().close();
        }catch(IOException e) {

        }catch(IndexOutOfBoundsException e) {

        }
        send("Kicking "+i);
        deleteThread(i);
        System.out.println("Kicked "+i);
    }

    /**
     * Used by readThread to end a connection
     */
    public static void deleteThread(int i) {
        r.remove(i);
        r2.remove(i);
        for(int w=0; w<r.size();w++) {
            r.get(w).setNumber(w);
        }
    }

    /**
     * Sends a message to every connected client in the format "Server: <message>"
     */
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

    /**
     * Sends message to socket "to" in the format "Server: <message>"
     */
    public static void send(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF("Server: "+message);
        }catch(IOException e) {

        }
    }

    /**
     * Used by readThread to send a message to another player
     */
    public static void sendTo(String message, int who) {
        try{
            new DataOutputStream(r.get(who).getSocket().getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

    /**
     * Sends message to socket "to" in the format "<message>"
     */
    public static void sendTo(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

    /**
     * Used by readThread to send chat messages
     */
    public static void send(Socket from, String message, String m2) {
        Socket client;
        System.out.println(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
            }catch(IOException e) {

            }
        }
    }

    /**
     * Used by inThread to receive input from server terminal
     */
    public static void setin(String m) {
        in=m;
    }

    /**
     * Returns a socket from a player number
     */
    public static Socket getSocket(int i) {
        return r.get(i).getSocket();
    }

    /**
     * Returns a name from a player number
     */
    public static String getName(int i) {
        return r.get(i).getName();
    }

    public static void catchUp(int x) {
        int one=0;
        int two=0;
        for(int i=0; i<r.size(); i++) {
            for(int w=0; w<r.get(i).getUser().getOwned().size(); w++) {
                if(i!=x) {
                    one=r.get(i).getUser().getOwned().get(w).getX();
                    two=r.get(i).getUser().getOwned().get(w).getY();
                    //System.out.println("Catching up! "+one+" "+two+" "+i);
                    send2("A E "+(one-r.get(x).getShiftX())+" "+(two-r.get(x).getShiftY()),x);
                }
            }
        }
    }
    
    public static void subLand(int x, int y) {
        Land current;
        for(int i=0; i<r.size(); i++) {
            for(int w=0; w<r.get(i).getUser().getOwned().size(); w++) {
                current = r.get(i).getUser().getOwned().get(w);
                if(current.getX()==x && current.getY()==y) {
                    r.get(i).getUser().getOwned().remove(w);
                    sendTo("You lost land!",i);
                }
            }
        }
    }

    public static void addToGrid(int x, int y, int z) {
        for(int i=0; i<r.size(); i++) {
            if(i!=z) {
                //System.out.println("In add to grid "+x+" "+y+" "+z);
                send2("A E "+(x-r.get(i).getShiftX())+" "+(y-r.get(i).getShiftY()),i);
            }
        }
    }

    public static void send2(String message, int i) {
        Socket client;
        //System.out.println("DEBUG: "+message+" "+i);
        client=r.get(i).getSocket2();
        try{
            new DataOutputStream(client.getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }
}