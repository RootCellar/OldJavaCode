import java.net.*;
import java.io.*;
import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;
public class gridThread implements Runnable {
    Socket server=null;
    public void run() {
        while(server==null) {
            try{
                Thread.sleep(100);
                server=Client.getGrid();
            }catch(Exception e) {

            }
        }
        DataInputStream in;
        try{
            in = new DataInputStream(server.getInputStream());
        }catch(Exception e) {
            return;
        }
        String in2="";
        ActorWorld world = new ActorWorld();
        UnboundedGrid grid = new UnboundedGrid();
        world.setGrid(grid);
        world.show();
        while(true) {
            try{
                in2=in.readUTF();
                System.out.println(in2);
                if(upToSpace(in2,true).equals("A")) {
                    in2=subUpToSpace(in2);
                    if(upToSpace(in2,true).equals("A")) {
                        in2=subUpToSpace(in2);
                        world.add(new Location( Integer.parseInt( upToSpace(in2,true)), Integer.parseInt( upToSpace( subUpToSpace(in2),true ))),new Rock(Color.GREEN));
                    }
                    if(upToSpace(in2,true).equals("E")) {
                        in2=subUpToSpace(in2);
                        world.add(new Location( Integer.parseInt( upToSpace(in2,true)), Integer.parseInt( upToSpace( subUpToSpace(in2),true ))),new Rock(Color.RED));
                    }
                }
            }catch(IOException e) {
                return;
            }catch(Exception e) {

            }
        }
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
}