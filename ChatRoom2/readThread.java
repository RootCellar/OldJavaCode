import java.net.*;
import java.io.*;
public class readThread implements Runnable,InputReader
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
        String in2="";
        while(readFrom==null || number==-1) {
        }
        Terminal term = new Terminal(this);
        while(true) {
            term.setTitle(readFrom.getRemoteSocketAddress()+" ("+name+")");
            try{
                in2=in.readUTF();
                if(in2.length()>=6 && in2.substring(0,6).equals("/name ")) {
                    name=in2.substring(6);
                    Server.send("Name set",readFrom);
                }
                else if(in2.equals("/help ")) {
                    Server.send("/help - Displays command list",readFrom);
                    Server.send("/name <name> - Set your username",readFrom);
                }
                else if(in2.length()>=5 && in2.substring(0,5).equals("/say ")) {
                    System.out.println("(/say) "+readFrom.getRemoteSocketAddress()+" ("+name+"): "+in2.substring(5));
                    Server.send(readFrom,in2.substring(5),name);
                }
                else {
                    System.out.println(readFrom.getRemoteSocketAddress()+" ("+name+"): "+in2);
                    term.write(readFrom.getRemoteSocketAddress()+" ("+name+"): "+in2);
                    //Server.send(readFrom,in2,name);
                }
            }catch(IOException e) {
                Server.deleteThread(number);
                System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+" Disconnected");
                term.setVisible(false);
                break;
            }
        }
    }
    
    public void use(String in) {
        Server.send(in,readFrom);
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