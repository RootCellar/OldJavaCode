import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Server
{
    static ArrayList<readThread> r=new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static ArrayList<String> questions = new ArrayList<String>();
    static ArrayList<String> answers = new ArrayList<String>();
    static String in="";
    static boolean started=false;
    static String question="";
    public static void main(String args[]) {
        System.out.println("Starting Server...");
        new File("Log").mkdir();
        if(args.length<1) {
            System.out.println("Umm...Port number not set..can't start server...");
            try{
                throw new Exception("Port number not set");
            }catch(Exception e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println("Starting threads..");
        inThread c = new inThread();
        Thread c2 = new Thread(c);
        c2.start();
        readThread t;
        Thread t2;
        ServerSocket server;
        boolean done=false;
        ArrayList<Integer> use = new ArrayList<Integer>();
        System.out.println("Opening socket..");
        try{
            server = new ServerSocket(Integer.parseInt(args[0]));
            server.setSoTimeout(100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Socket client=null;
        System.out.println("Initializing game...");
        getQuestions();
        getAnswers();
        System.out.println("Server Started.");
        System.out.println("Use /help to get command list.");
        System.out.println("Port: "+Integer.parseInt(args[0]));
        while(!done) {
            try{
                try{
                    client = null;
                    client = server.accept();
                    try{
                        Thread.sleep(100);
                    }catch(Exception d) {
                    }
                    if(client!=null) {
                        System.out.println("Connected to "+client.getRemoteSocketAddress());
                        System.out.println("Setting up threads...");
                        send(client.getRemoteSocketAddress()+" connected.");
                        t=new readThread();
                        t2=new Thread(t);
                        t2.start();
                        t.setSocket(client);
                        if(started==true) {
                            sendTo("Sorry, game is already started!",client);
                            sendTo("You will now be kicked.",client);
                            System.out.println(client.getRemoteSocketAddress()+" disconnected (game started)");
                            t2.stop();
                            client.close();
                        }
                        else if(r.size()<5) {
                            r.add(t);
                            r2.add(t2);
                            t.setNumber(r.size()-1);
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
                if(in.equals("/start")) {
                    started=true;
                    send("Game Started! No more players will be accepted!");
                    in="/newturn";
                }
                else if(in.equals("/newturn")) {
                    if(started==false) {
                        System.out.println("Game not Started!");
                    }
                    else {
                        send("Starting new turn..");
                        question=questions.get(new Random().nextInt(questions.size()));
                        for(int i=0; i<r.size(); i++) {
                            for(int w=0; w<10; w++) {
                                use.add(new Integer(new Random().nextInt(answers.size())));
                            }
                            r.get(i).setHand(use);
                            r.get(i).newTurn();
                            while(use.size()>0) {
                                use.remove(0);
                            }
                        }
                        send("Hands set, question set");
                        send(question);
                    }
                    in="";

                }
                else if(in.equals("/help")) {
                    System.out.println("/Stop - Stop the server");
                    System.out.println("/help - view command list");
                    System.out.println("/list - view player list. \nIt shows number, ip, name(if set), is done picking answers, and some debug info");
                    System.out.println("/kick <number> - use /list to \nfind the number of the player, than use /kick (number) to disconnect them");
                    System.out.println("/kickall - kicks every player \noff of the server");
                    in="";
                }
                else if(in.equals("/list")) {
                    in="";
                    for(int i=0; i<r.size(); i++) {
                        System.out.println(i+". "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName()+" "+r.get(i).isDone()+" "+r.get(i).getNumber());
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

    public static void getQuestions() {
        try{
            File f = new File("questions.txt");
            if(f.canRead()==false) {
                f.createNewFile();
            }
            Scanner read = new Scanner(f);
            while(read.hasNextLine()) {
                String read2=read.nextLine();
                questions.add(read2);
                System.out.println(read2);
            }
        }catch(Exception e) {

        }
    }

    public static void getAnswers() {
        try{
            File f = new File("answers.txt");
            if(f.canRead()==false) {
                f.createNewFile();
            }
            Scanner read = new Scanner(f);
            while(read.hasNextLine()) {
                String read2=read.nextLine();
                answers.add(read2);
                System.out.println(read2);
            }
        }catch(Exception e) {

        }
    }

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

    public static void deleteThread(int i) {
        r.remove(i);
        r2.remove(i);
        for(int w=0; w<r.size();w++) {
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

    public static void sendTo(String message, int who) {
        try{
            new DataOutputStream(r.get(who).getSocket().getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

    public static void sendTo(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

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

    public static void setin(String m) {
        in=m;
    }

    public static Socket getSocket(int i) {
        return r.get(i).getSocket();
    }

    public static String getName(int i) {
        return r.get(i).getName();
    }

    public static boolean isStarted() {
        return started;
    }

    public static String getQuestion() {
        return question;
    }
}