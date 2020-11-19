//Darian Marvel
//Allies

/**
 * A class controlling allies
 * @author Darian Marvel
 * @version 1.0
 */
public class Ally
{
    private int lvl;
    private double hp;
    private int atk;
    private int def;
    private boolean isAlive;
    private boolean didAttack;
    /**
     * The constructor creating the allies
     * @param l Level of the ally
     */
    public Ally(int l) {
        lvl=l;
        hp=lvl*10;
        atk=lvl*5;
        def=lvl*5;
        isAlive=true;
        didAttack=false;
    }
    
    //Getters
    
    public boolean DidAttack() {
        return didAttack;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public int GetLvl() {
        return lvl;
    }
    
    public int GetAtk() {
        return atk;
    }
    
    public int GetDef() {
        return def;
    }
    
    public double GetHp() {
        return hp;
    }
    
    //Setters
    
    public void Die() {
        isAlive=false;
    }
    
    public void Revive() {
        isAlive=true;
    }
    
    public void SetHp(double x) {
        hp=x;
    }
    
    public void Attack() {
        didAttack=true;
    }
    
    public void RAttack() {
        didAttack=false;
    }
    
}