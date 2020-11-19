public class Ally extends Mob
{
    public Ally(Game game) {
        super(game);
    }
    
    public void render(PixelCanvas c) {
        c.setPixel(x,y,0,0,255);
    }
    
    public void ai() {
        int tx=g.p.x;
        int ty=g.p.y;
        if(x<tx-10) addX(1);
        if(x>tx+10) addX(-1);
        if(y<ty-10) addY(1);
        if(y>ty+10) addY(-1);
    }
}