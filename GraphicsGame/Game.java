public class Game implements Runnable
{
    public PixelCanvas screen = new PixelCanvas();
    public InputListener input = new InputListener(this);
    public int x = 0;
    public int y = 0;
    public void Game() {
        new Thread(this).start();
    }

    public void run() {
        while(true) {
            try{
                Thread.sleep(10);
                if(input.down.down) y=y+1;
                if(input.up.down) y=y-1;
                if(input.left.down) x=x-1;
                if(input.right.down) x=x+1;
                if(x<10) x=10;
                if(y<10) y=10;
                if(x>screen.WIDTH-10) x = screen.WIDTH-10;
                if(y>screen.HEIGHT-10) y = screen.HEIGHT-10;
                screen.clear();
                screen.setPixel(x,y,255,255,255);
                screen.setPixel(x+1,y,255,255,255);
                screen.setPixel(x,y+1,255,255,255);
                screen.setPixel(x+1,y+1,255,255,255);
                screen.render();
            }catch(Exception e) {

            }
        }
    }
}