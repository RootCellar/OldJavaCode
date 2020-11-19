import java.util.ArrayList;
public class Player
{
    private ArrayList<Tank> avail;
    //private int exp;
    public Player() {
        avail = new ArrayList<Tank>();
    }
    
    /**
     * Returns the player's available tanks
     */
    public ArrayList<Tank> getTanks() {
        return avail;
    }
    
    public void setTanks(ArrayList<Tank> from) {
        avail = from;
    }
}