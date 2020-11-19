import java.util.ArrayList;
public class Player
{
    private int money;
    ArrayList<Land> owned;
    public Player() {
        money=1000;
        owned = new ArrayList<Land>();
    }
    
    public void subMoney(int x) {
        money-=x;
    }
    
    public void addMoney(int x) {
        money+=x;
    }
    
    public void setMoney(int x) {
        money=x;
    }
    
    public int getMoney() {
       return money;
    }
    
    public ArrayList<Land> getOwned() {
        return owned;
    }
    
    public void addLand(Land get) {
        owned.add(get);
    }
    
    public void removeLand(int i) {
        owned.remove(i);
    }
}