import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String args[]) {
        System.out.println("Starting Server...");
        ServerSocket server;
        try{
            server = new ServerSocket(25565);
            server.setSoTimeout(1000);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Socket client=null;
        while(true) {
            try{
                client = null;
                client = server.accept();
                if(client!=null) {
                    System.out.println("Connected to "+client.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    System.out.println(in.readUTF());
                    new DataOutputStream(client.getOutputStream()).writeUTF("Thanks for connecting! Bye!");
                    client.close();
                }
            }catch(SocketTimeoutException s) {
                
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}