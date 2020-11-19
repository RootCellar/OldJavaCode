import java.net.*;
import java.io.*;
import java.util.ArrayList;
public class readThread implements Runnable
{
    Socket readFrom = null;
    String name="";
    int number=-1;
    String use="";
    boolean isDone=false;
    ArrayList<Integer> chosen = new ArrayList<Integer>();
    ArrayList<Integer> hand = new ArrayList<Integer>();
    public void run() {
        DataInputStream in=null;
        try{
            in = new DataInputStream(readFrom.getInputStream());
        }catch(IOException e) {

        }
        String in2="";
        while(readFrom==null || number==-1) {
            try{
                Thread.sleep(100);
            }catch(Exception e) {

            }
        }
        Server.sendTo("Recieved number and socket. Welcome to cards against humanity!",readFrom);
        Server.sendTo("What is your name?",readFrom);
        try{
            in2=in.readUTF();
            name=in2;
        }catch(IOException e) {

        }
        Server.sendTo("Name chosen! Please wait for game to start..",readFrom);
        Server.sendTo("Use /help to see the list of commands",readFrom);
        while(true) {
            try{
                in2=in.readUTF();
                Server.send(readFrom,in2,name);
                if(in2.equals("/help ")) {
                    Server.sendTo("/hand - View hand",readFrom);
                    Server.sendTo("/done - be done selecting answer(s) (No new answers can be selected)",readFrom);
                }
                if(in2.equals("/done ")) {
                    if(Server.isStarted()==false) {
                        Server.sendTo("Game not started!",readFrom);
                    }else {
                        isDone=true;
                        Server.sendTo("Done picking answers!",readFrom);
                    }
                }
                if(in2.equals("/hand ")) {
                    if(Server.isStarted()==false) {
                        Server.sendTo("Game not started!",readFrom);
                    }
                    else {
                        for(int i=0; i<hand.size(); i++) {
                            Server.sendTo(i+" "+Server.answers.get(hand.get(i)),readFrom);
                        }
                    }
                }
                
            }catch(IOException e) {
                //System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+" Disconnected");
                Server.deleteThread(number);
                try{
                    readFrom.close();
                }catch(IOException f) {

                }
                break;
            }catch(Exception e) {
                Server.send("Server error");
                e.printStackTrace();
            }
        }
    }

    public void setHand(ArrayList<Integer> h) {
        while(hand.size()>0) {
            hand.remove(0);
        }
        while(h.size()>0) {
            hand.add(h.get(0));
            h.remove(0);
        }
    }

    public void setNumber(int num) {
        number=num;
    }

    public void setSocket(Socket use) {
        readFrom=use;
    }

    public Socket getSocket() {
        return readFrom;
    }

    public String getName() {
        return name;
    }

    public void subNumber() {
        number--;
    }

    public int getNumber() {
        return number;
    }

    public boolean isDone() {
        return isDone;
    }

    public void newTurn() {
        isDone=false;
        while(chosen.size()>0) {
            chosen.remove(0);
        }
    }
}