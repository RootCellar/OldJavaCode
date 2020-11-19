import java.net.*;
import java.io.*;
import java.util.Random;
/**
 * This class is run as a separate thread to handle player commands
 */
public class readThread implements Runnable
{
    Socket readFrom = null;
    Socket gridTo = null;
    String name="";
    int number=-1;
    String use="";
    Player user;
    int shiftx;
    int shifty;
    String[] args;
    boolean use2;
    public void run() {
        DataInputStream in=null;
        String in2="";
        user= new Player();
        while(readFrom==null || number==-1 || gridTo==null) {
            try{
                Thread.sleep(100);
            }catch(Exception e) {

            }
        }
        try{
            in = new DataInputStream(readFrom.getInputStream());
        }catch(IOException e) {

        }
        shiftx= new Random().nextInt(10)-5;
        shifty= new Random().nextInt(10)-5;
        Server.subLand(shiftx,shifty);
        addToGrid(0,0,"A");
        user.addLand(new Land(shiftx,shifty));
        Server.addToGrid(shiftx, shifty, number);
        //System.out.println(shiftx+" "+shifty);
        while(true) {
            try{
                in2=in.readUTF();
                try{
                    Thread.sleep(100);
                }catch(Exception e) {

                }
                if(in2.equals("/money")) {
                    Server.sendTo("You have "+user.getMoney()+" dollars",readFrom);
                }
                if(Server.upToSpace(in2,true).equals("/claim")) {
                    args=Server.getArgs(in2);
                    use2=false;
                    for(int i=0; i<user.getOwned().size(); i++) {
                        if(getDistance(user.getOwned().get(i),new Land(Integer.parseInt(args[0])+shiftx,Integer.parseInt(args[1])+shifty))<2) {
                            use2=true;
                        }
                    }
                    if(Integer.parseInt(args[0])+shiftx>100 || Integer.parseInt(args[1])+shifty>100 || Integer.parseInt(args[0])+shiftx<-100 || Integer.parseInt(args[1])+shifty<-100 ) {
                        Server.sendTo("That location is outside of area bounds.",readFrom);
                    }
                    else if(use2==true && user.getMoney()>=500) {
                        Server.subLand(Integer.parseInt(args[0])+shiftx,Integer.parseInt(args[1])+shifty);
                        addToGrid(Integer.parseInt(args[0]),Integer.parseInt(args[1]),"A");
                        user.addLand(new Land(Integer.parseInt(args[0])+shiftx,Integer.parseInt(args[1])+shifty));
                        Server.addToGrid(Integer.parseInt(args[0])+shiftx,Integer.parseInt(args[1])+shifty,number);
                        user.subMoney(500);
                    }
                    else if(user.getMoney()<500) {
                        Server.sendTo("Not enough money!",readFrom);
                    }
                    else {
                        Server.sendTo("Land is too far away!",readFrom);
                    }
                }
                if(in2.equals("/lands")) {
                    for(int i=0; i<user.getOwned().size(); i++) {
                        Server.sendTo("Land "+i+": "+(user.getOwned().get(i).getX()-shiftx)+" "+(user.getOwned().get(i).getY()-shifty),readFrom);
                    }
                }
                if(in2.length()>=1 && !in2.substring(0,1).equals("/")){
                    Server.send(readFrom,in2,name);
                }
                Server.getListener().command(in2,this);
            }catch(IOException e) {
                //System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+"("+name+") Disconnected");
                Server.deleteThread(number);
                try{
                    readFrom.close();
                }catch(IOException f) {

                }
                return;
            }
            catch(Exception e) {
                Server.send("Server error");
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the distance between two locations
     */
    public static double getDistance(Land one, Land two) {
        int x1=one.getX();
        int y1=one.getY();
        int x2=two.getX();
        int y2=two.getY();
        return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
    }

    public int getShiftX() {
        return shiftx;
    }

    public int getShiftY() {
        return shifty;
    }

    public void addToGrid(int x, int y, String z) {
        try{
            new DataOutputStream(gridTo.getOutputStream()).writeUTF("A A "+x+" "+y);
        }catch(Exception e) {

        }
    }

    public void setNumber(int num) {
        number=num;
    }

    public void setSocket(Socket use) {
        readFrom=use;
    }

    public void setSocket2(Socket use) {
        gridTo=use;
    }

    /**
     * Returns the socket connection to the player
     */
    public Socket getSocket() {
        return readFrom;
    }

    public Socket getSocket2() {
        return gridTo;
    }

    /**
     * Returns the player's name
     */
    public String getName() {
        return name;
    }

    public void subNumber() {
        number--;
    }

    public int getNumber() {
        return number;
    }

    public void setPlayer(Player newplayer) {
        user=newplayer;
    }

    /**
     * Returns the object containing the player info
     */
    public Player getUser() {
        return user;
    }
}