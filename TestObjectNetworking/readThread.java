import java.net.*;
import java.io.*;
public class readThread implements Runnable
{
    Socket readFrom = null;
    String name="";
    int number=-1;
    public void run() {
        DataInputStream in=null;
        try{
            in = new DataInputStream(readFrom.getInputStream());
        }catch(IOException e) {

        }
        Object in3;
        String in2="";
        while(readFrom==null || number==-1) {
        }
        while(true) {
            try{
                in3=new ObjectInputStream(in).readObject();
                System.out.println(in3.toString());
                in2=((Packet)in3).value;
                if(in2.length()>=6 && in2.substring(0,6).equals("/name ")) {
                    name=in2.substring(6,in2.length()-1);
                    Server.send("Name set",readFrom);
                }
                else if(in2.equals("/help ")) {
                    Server.send("/help - Displays command list",readFrom);
                    Server.send("/name <name> - Set your username",readFrom);
                }
                else {
                    System.out.println(readFrom.getRemoteSocketAddress()+" ("+name+"): "+in2);
                    Server.send(readFrom,in2,name);
                }
            }catch(ClassNotFoundException e) {
                
            
            }catch(IOException e) {
                Server.deleteThread(number);
                System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+" Disconnected");
                break;
            }catch(Exception e) {
            
            }
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
}