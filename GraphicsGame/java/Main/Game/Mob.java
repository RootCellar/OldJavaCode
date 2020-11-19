public class Mob
{
    int x = 0;
    int y = 0;
    Game g;
    
    public Mob(Game game) {
        g=game;
    }
    
    public void addX(int a) {
        if( !g.isUsed(x+a,y) ) x+=a;
    }
    
    public void addY(int a) {
        if( !g.isUsed(x,y+a) ) y+=a;
    }
    
    public void render(PixelCanvas c) {
        c.setPixel(x,y,255,255,255);
    }
    
    public void collision() {
        if(x<10) x=10;
        if(x>690) x=690;
        if(y<10) y=10;
        if(y>690) y=690;
    }
    
    public void randomPos() {
        x = (int)((Math.random()*680) + 10);
        y = (int)((Math.random()*680) + 10);
    }

}