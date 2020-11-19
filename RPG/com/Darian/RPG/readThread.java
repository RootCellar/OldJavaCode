package com.Darian.RPG;
import java.net.*;
import java.io.*;
import java.util.Random;
/**
 * This class is run as a separate thread to handle player commands
 */
public class readThread implements Runnable
{
    Socket readFrom = null;
    String name="";
    String use="";
    String use2="";
    String pass="";
    int number=-1;
    boolean isLoggedIn=false;
    Character user=null;
    boolean isGoing=true;
    boolean isDone=false;
    int gold=0;
    boolean client=false;
    public void run() {
        try{
            DataInputStream in=null;
            String in2="";
            while((readFrom==null || number==-1)) {
                try{
                    Thread.sleep(100);
                }catch(Exception e) {

                }
            }
            try{
                in = new DataInputStream(readFrom.getInputStream());
            }catch(Exception e) {
                return;
            }

            if(Server.isHalted()) {
                Server.sendTo("Sorry, server is halted. Commands are blocked.",readFrom);
                while(Server.isHalted()) {
                    try{
                        Thread.sleep(100);
                    }catch(Exception e) {

                    }
                }
                Server.sendTo("Server is no longer halted",readFrom);
            }

            client=true;
            try{
                while(!isLoggedIn) {
                    Server.sendTo("/login or /register?",readFrom);
                    in2=in.readUTF();
                    Thread.sleep(100);
                    if(in2.equals("/login")) {
                        Server.sendTo("Username: ",readFrom);
                        use=in.readUTF();
                        Server.sendTo("Password: ",readFrom);
                        pass=in.readUTF();
                        in2=Server.authen(use,pass,this);
                        Server.sendTo(in2,readFrom);
                        if(in2.equals("Logged in")) {
                            name=use;
                            Server.read(this,user);
                            isLoggedIn=true;
                            Server.log(use+" logged in!");
                        }
                    }
                    if(in2.equals("/register")) {
                        Server.sendTo("Username: ",readFrom);
                        use=in.readUTF();
                        Server.sendTo("Password: ",readFrom);
                        pass=in.readUTF();
                        in2=Server.authenr(use,pass,this);
                        Server.sendTo(in2,readFrom);
                        if(in2.equals("Register")) {
                            Server.sendTo("Log in to your new account to continue",readFrom);
                            Server.log("Account with username "+use+" and password "+pass+" has been created!");
                        }
                    }
                }

                while(user.getType()==null) {
                    for(int i=0; i<Server.getClasses().size(); i++) {
                        if(Server.getClasses().get(i).isAvail()) {
                            Server.sendTo(i+". "+Server.getClasses().get(i).getName(),readFrom);
                        }
                    }
                    Server.sendTo("Pick a class by number: ",readFrom);
                    in2=in.readUTF();
                    Thread.sleep(100);
                    try{
                        if(Server.getClasses().get( Integer.parseInt(in2) ).isAvail()) {
                            user.setClass(Server.getClasses().get(Integer.parseInt(in2)));
                        } else {
                            Server.sendTo("Not an option",readFrom);
                        }
                    }catch(Exception e) {
                        Server.sendTo("Please type an integer",readFrom);
                    }
                }

                while(user.getRace()==null) {
                    for(int i=0; i<Server.getRaces().size(); i++) {
                        Server.sendTo(i+". "+Server.getRaces().get(i).getName(),readFrom);
                        Thread.sleep(100);
                    }
                    Server.sendTo("Pick a race by number: ",readFrom);
                    in2=in.readUTF();
                    Thread.sleep(100);
                    try{
                        user.setRace(Server.getRaces().get(Integer.parseInt(in2)));
                    }catch(Exception e) {
                        Server.sendTo("Please type an integer",readFrom);
                    }
                }

                Server.characters.add(user);
                user.revive();
                user.setX(0);
                user.setY(0);
                while(isGoing) {
                    if(Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    in2=in.readUTF();
                    Server.log(name+":"+in2);
                    Thread.sleep(100);
                    if(Server.isHalted()) {
                        Server.sendTo("Sorry, server is halted",readFrom);
                        in2="";
                    }
                    user.calcStats();
                    if(in2.equals("/help")) {
                        Server.sendTo("/mychar - gives info about your character",readFrom);
                        Server.sendTo("/look - look around",readFrom);
                        Server.sendTo("/quit - exit the game",readFrom);
                    }
                    else if(in2.equals("/mychar")) {
                        Server.sendTo(user.toString(),readFrom);
                    }
                    /*
                    else if(in2.equals("/spot")) {
                    Server.sendTo("Running spotting system...",readFrom);
                    Server.sendTo(Server.spot(user),readFrom);
                    }
                     */
                    else if(in2.equals("/look")) {
                        Server.sendTo("Looking around...",readFrom);
                        Server.sendTo(Server.look(user),readFrom);
                    }
                    else if(in2.equals("/quit")) {
                        readFrom.close();
                    }
                    else if(!in2.equals("") && !in2.substring(0,1).equals("/")) {
                        Server.send(readFrom,in2,name);
                        //Server.gui2.write(name+": "+in2);
                    }
                    Server.getListener().command(in2,this);
                    Server.save(this,name,pass);
                }
            }catch(IOException e) { //Connection closed, player is quitting
                //System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+"("+name+") Disconnected");
                Server.log(name+" disconnected!");
                //Server.deleteThread(number);
                isGoing=false;
                try{
                    readFrom.close();
                }catch(IOException f) {

                }
            }
        }catch(InterruptedException e) {

        }
        Server.save(this,name,pass);
        Server.gui2.write("Saved "+name);
        Server.log("Saved "+name);
        isDone=true;
    }

    public boolean isDone() {
        return isDone;
    }

    public void stopGoing() {
        isGoing=false;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int i) {
        gold=i;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setNumber(int num) {
        number=num;
    }

    public void setSocket(Socket use) {
        readFrom=use;
    }

    /**
     * Returns the socket connection to the player
     */
    public Socket getSocket() {
        return readFrom;
    }

    /**
     * Returns the player's name
     */
    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Character getUser() {
        return user;
    }

    public void setUser(Character u) {
        user=u;
    }
}