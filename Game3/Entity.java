public class Entity
{
    private int level=0;
    /** New stats to make things easier */
    private int baseHealth=0;
    private int baseAttack=0;
    private int baseDefense=0;
    private int xpMult=3;
    private double lvlMult=1.1;
    //end new stats
    private int health=0;
    private int attack=0;
    private int defense=0;
    private int maxHealth=0;
    private int xp=0;
    private boolean isABot=true;
    private boolean isAlive=true;
    private String type="";
    private int x;
    private int y;
    public Entity() {
    }
    
    public void calc() {
        setLevel(xpMult);
        setMaxHealth(baseHealth, lvlMult);
        setAttack(baseAttack, lvlMult);
        setDefense(baseDefense, lvlMult);
    }

    public void setX(int x2) {
        x=x2;
    }

    public void setY(int y2) {
        y=y2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void revive() {
        isAlive=true;
    }

    public void die() {
        isAlive=false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setType(String t) {
        type=t;
    }

    public String getType() {
        return type;
    }

    public void calcStats(int b, double m) {
        this.setMaxHealth(b,m);
        this.setAttack(b,m);
        this.setDefense(b,m);
    }

    public void setMaxHealth(int b, double m) { 
        double a=b;
        double x=m;
        double y=level;
        double n=a*Math.pow(x,y);
        maxHealth=(int) n;
    }

    public void setAttack(int b, double m) { 
        double a=b;
        double x=m;
        double y=level;
        double n=a*Math.pow(x,y);
        attack=(int) n;
    }

    public void setDefense(int b, double m) { 
        double a=b;
        double x=m;
        double y=level;
        double n=a*Math.pow(x,y);
        defense=(int) n;
    }

    public void setHealth(int h) {
        health=h;
    }

    public void addHealth(int h) {
        health+=h;
    }

    public void subHealth(int h) {
        health-=h;
    }

    public void addXp(int x) {
        xp+=x;
    }

    public void subXp(int x) {
        xp-=x;
    }

    public void setXp(int x) {
        xp=x;
    }

    public void setLevel(int m) {
        double xpNeeded=0;
        int x=0;
        while(xpNeeded<=xp) {
            x++;
            xpNeeded=Math.pow(x,m);
        }
        level=x-1;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getXp() {
        return xp;
    }

    public void setBot(boolean b) {
        isABot=b;
    }

    public boolean isABot() {
        return isABot;
    }
    
    public void setBaseHealth(int n) {
        baseHealth=n;
    }
    
    public void setBaseAttack(int n) {
        baseAttack=n;
    }
    
    public void setBaseDefense(int n) {
        baseDefense=n;
    }
    
    public void setXPMult(int n) {
        xpMult=n;
    }

    public void setLvlMult(double n) {
        lvlMult=n;
    }
    
    public String toString() {
        return "Level: "+level+"\n"+"Health: "+health+"\n"+"maxHealth: "+maxHealth+"\n"+"Attack: "+attack+"\n"+"Defense: "+defense+"\n"+"Experience: "+xp+"\n"+"Type: "+type+"\n"+"isABot: "+isABot+"\n"+"isAlive: "+isAlive+"\n"+x+"\n"+y;
    }
}