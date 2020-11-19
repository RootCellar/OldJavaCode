import java.util.ArrayList;
/**
 * This thread is used to reload tank guns and make sure that players don't leave the area boundaries
 */
public class checker implements Runnable
{
    public void run() {
        while(true) {
            for(int i=0; i<Server.getThreads().size(); i++) {
                if(Server.getThreads().get(i).getUser()==null) {
                    continue;
                }
                int x=Server.getThreads().get(i).getUser().getOwned().size();
                Server.getThreads().get(i).getUser().addMoney(x);
                if(Server.getThreads().get(i).getUser().getMoney()>100000000) {
                    Server.getThreads().get(i).getUser().setMoney(100000000);
                }
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e) {
                
            }
        }
    }
}