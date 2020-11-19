import java.util.ArrayList;
/**
 * This thread is used to reload tank guns and make sure that players don't leave the area boundaries
 */
public class reloader implements Runnable
{
    Tank current;
    int mapSize=100;
    public void run() {
        while(true) {
            //Loops players, reloads guns, keeps them in map bounds
            for(int i=0; i<Server.getPlayers().size(); i++) {
                    current=Server.getPlayers().get(i);
                    current.doReload();
                    if(current.getX()>mapSize) {
                        current.setX(mapSize);
                    }
                    if(current.getX()<0-mapSize) {
                        current.setX(0-mapSize);
                    }
                    if(current.getY()>mapSize) {
                        current.setY(mapSize);
                    }
                    if(current.getY()<0-mapSize) {
                        current.setY(0-mapSize);
                    }
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e) {
                
            }
        }
    }
    
    public void setMapSize(int x) {
        mapSize=x;
    }
    
    public int getMapSize() {
        return mapSize;
    }
}