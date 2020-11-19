import java.util.ArrayList;
public class ClientRunner
{
    public static void main(String args[]) {
        ArrayList<Client> list = new ArrayList<Client>();
        ArrayList<Thread> list2 = new ArrayList<Thread>();
        Client x;
        Thread x2;
        for(int i=0; i<500; i++) {
            /*
            try{
                Thread.sleep(1000);
            }catch(Exception e) {
                
            }
            */
            x = new Client();
            x2=new Thread(x);
            x2.start();
            list.add(x);
            list2.add(x2);
            x.setNumber(i);
        }
    }
}