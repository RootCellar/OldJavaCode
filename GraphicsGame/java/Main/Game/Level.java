import java.io.*;
import java.util.ArrayList;
public class Level implements Serializable
{
    static long serialVersionUID=0;
    int id;
    int x=0;
    int y=0;
    int tx=500;
    int ty=500;
    Monster[] monsters = new Monster[100];
    public Level() {
        id = hashCode();
        for(int i=0; i<monsters.length; i++) {
            monsters[i] = new Monster();
        }   
    }
    
    public void tick() {
        
        for(int i=0; i<monsters.length; i++) {
            monsters[i].ai();
        }
        
        if(x<tx) x++;
        if(y<ty) y++;
        if(x>tx) x--;
        if(y>ty) y--;
        
        if(x==tx) tx=(int)(Math.random()*500);
        if(y==ty) ty=(int)(Math.random()*500);
        /**
        if(x==tx && y==ty) {
            tx=(int)(Math.random()*500);
            ty=(int)(Math.random()*500);
        }
        */
    }
}