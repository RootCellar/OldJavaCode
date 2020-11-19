import java.net.*;
import java.io.*;
public class SocketHandler implements Runnable
{
    private static int waitTime=100;
    private boolean going;
    private boolean connected;
    private static boolean globalGoing=true;
    private Socket client;
    private InputUser user;
    public SocketHandler(Socket s) {
        client=s;
        connected=true;
        new Thread(this).start();
        going=true;
    }
    
    public boolean isGoing() {
        return going;
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void setUser(InputUser i) {
        user=i;
    }

    public void send(String s) throws IOException {
        new DataOutputStream(client.getOutputStream()).writeUTF(s);
    }

    public void close() {
        try{
            client.close();
        }catch(Exception e) {

        }
        going=false;
        connected=false;
    }

    public void run() {
        DataInputStream in=null;
        try{
            in = new DataInputStream(client.getInputStream());
        }catch(Exception e) {
            
        }
        while(going && globalGoing) {
            try{
                Thread.sleep(waitTime);
            }catch(Exception e) {

            }
            try{
                user.inputText(in.readUTF());
            }catch(IOException e) {
                connected=false;
                going=false;
            }catch(Exception e) {
                
            }

        }
        going=false;
        connected=false;
    }

    public static void setWaitTime(int x) {
        waitTime=x;
    }

    public static void setGoing(boolean b) {
        globalGoing=b;
    }
}