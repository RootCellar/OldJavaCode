package com.Darian.RPG;
import java.util.ArrayList;
/**
 * 
 */
public class checker implements Runnable
{
    int mapSize=100;
    public void run() {
        while(!Server.done) {
            long start = System.nanoTime();
            if(!Server.isHalted()) {
                for(int i=0; i<Server.getCharacters().size(); i++) {
                    Server.getCharacters().get(i).calcStats();
                    Server.getCharacters().get(i).regen();
                    Server.getCharacters().get(i).regenMana();
                    if(Server.getCharacters().get(i).getX()>mapSize) {
                        Server.getCharacters().get(i).setX(mapSize);
                    }
                    if(Server.getCharacters().get(i).getY()>mapSize) {
                        Server.getCharacters().get(i).setY(mapSize);
                    }
                    if(Server.getCharacters().get(i).getX()<0) {
                        Server.getCharacters().get(i).setX(0);
                    }
                    if(Server.getCharacters().get(i).getY()<0) {
                        Server.getCharacters().get(i).setY(0);
                    }
                    if(!Server.isPlayer(Server.getCharacters().get(i)) && !Server.getCharacters().get(i).isAlive()) {
                        Server.gui2.write(Server.getCharacters().get(i).getName()+" died.");
                        Server.getCharacters().get(i).remove();
                        Server.getCharacters().remove(i);
                    }
                }
            }
            Server.stableWait(100,start);
        }
    }

    public void setMapSize(int x) {
        mapSize=x;
    }

    public int getMapSize() {
        return mapSize;
    }
}