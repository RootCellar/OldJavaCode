import java.net.*;
public class Tank
{
    private String name;
    private String type;
    private String type2;
    private int armorFront;
    private int armorSide;
    private int armorRear;
    private int reload;
    private int reload2;
    private boolean isAlive;
    private String facing;
    private int pen;
    private int x;
    private int y;
    private double viewRange;
    private double camo;
    private String job="";
    private int number;
    private int maxGear;
    private int gear;
    private double acc;
    
    public void setAcc(double x) {
        acc=x;
    }
    
    public double getAcc() {
        return acc;
    }
    
    public int getMaxGear() {
        return maxGear;
    }
    
    public void setGear(int w) {
        gear=w;
    }
    
    public int getGear() {
        return gear;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int u) {
        number=u;
    }

    public void setJob(String j) {
        job=j;
    }

    public String getJob() {
        return job;
    }

    public void setAiType(String t) {
        type2=t;
    }

    public String getAiType() {
        return type2;
    }

    public String toString() {
        return "Name: "+name
        +"\n"
        +"armorFront: "+armorFront
        +"\n"
        +"armorSide: "+armorSide
        +"\n"
        +"armorRear: "+armorRear
        +"\n"
        +"Reload: "+reload
        +"\n"
        +"isAlive: "+isAlive
        +"\n"
        +"Facing: "+facing
        +"\n"
        +"Pen: "+pen
        +"\n"
        +"X: "+x
        +"\n"
        +"Y: "+y
        +"\n"
        +"viewRange: "+viewRange
        +"\n"
        +"camo: "+camo
        +"\n"
        +"maxGear: "+maxGear
        +"\n"
        +"accuracy: "+acc
        +"\n";
    }

    public void doJob(String x) {

    }

    public void setType(String x) {
        type=x;
    }

    public String getType() {
        return type;
    }

    /**
     * Constructs the tank
     * @param n The name of the tank
     * @param f Frontal armor thickness
     * @param s Side armor thickness
     * @param r Rear armor thickness
     * @param r2 Reload time, in ticks
     * @param p Penetration
     * @param f2 Facing Direction
     * @param v View Range
     * @param c camo
     * @version 1.0
     */
    public Tank(String n, int f, int s, int r, int r2, int p, String f2, double v, double c, int g, double a) {
        name=n;
        armorFront=f;
        armorSide=s;
        armorRear=r;
        reload=r2;
        pen=p;
        isAlive=true;
        facing=f2;
        viewRange=v;
        camo=c;
        type="Bot";
        maxGear=g;
        acc=a;
    }

    /**
     * Makes this tank the same as the other tank
     */
    public Tank(Tank other) {
        name=other.getName();
        armorFront=other.getFront();
        armorSide=other.getSide();
        armorRear=other.getRear();
        reload=other.getReload();
        pen=other.getPen();
        isAlive=true;
        facing=other.getFacing();
        viewRange=other.getViewRange();
        camo=other.getCamo();
        maxGear=other.getMaxGear();
        acc=other.getAcc();
    }

    public double getCamo() {
        return camo;
    }

    public void setCamo(double x) {
        camo=x;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void shootP(Tank other) { //Not used
        int np=0;
        if(other.isAlive()==false) {
            System.out.println("Target not alive! (Coding Error)");
        }
        boolean diag=false;
        boolean diag2=false;
        boolean isAngle=true;
        int w=0;
        if(Math.abs(x-other.getX())>Math.abs(y-other.getY())) {
            diag=true;
        }
        if(Math.abs(y-other.getY())>Math.abs(x-other.getX())) {
            diag2=true;
        }
        if(reload2<=0) {
            if(x==other.getX() && y==other.getY()) {
                np=other.getFront();
                isAngle=false;
            }
            else if(other.getFacing().equals("North")) {
                if(y>other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y<other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y>other.getY()) {
                    np=other.getFront();
                    w=1;
                }
                else if(y<other.getY()) {
                    np=other.getRear();
                    w=1;
                }
                else if(y==other.getY()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("South")) {
                if(y>other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y<other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y>other.getY()) {
                    np=other.getRear();
                    w=1;
                }
                else if(y<other.getY()) {
                    np=other.getFront();
                    w=1;
                }
                else if(y==other.getY()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("West")) {
                w=1;
                if(x>other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x<other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x>other.getX()) {
                    np=other.getRear();
                    w=0;
                }
                else if(x<other.getX()) {
                    np=other.getFront();
                    w=0;
                }
                else if(x==other.getX()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("East")) {
                w=1;
                if(x>other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x<other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x>other.getX()) {
                    np=other.getFront();
                    w=0;
                }
                else if(x<other.getX()) {
                    np=other.getRear();
                    w=0;
                }
                else if(x==other.getX()) {
                    np=other.getSide();
                }
            }
            if(isAngle) {
                np=calcEffArmor(other, np, w);
            }
            if(np<=pen) {
                System.out.println("Enemy "+other.getName()+" destroyed!");
                other.destroy();
            }
            else {
                System.out.println("Shot bounced off!");
            }
            reload2=reload;
        }
        else {
            System.out.println("Gun is not loaded!");
        }
    }

    /**
     * Shoots at another tank
     * This method decides whether a shot penetrates or not
     * And uses trigonometry to calculate effective armor thickness
     */
    public String shoot(Tank other) {
        int np=0;
        boolean diag=false;
        boolean diag2=false;
        boolean isAngle=true;
        int w=0;
        if(Math.abs(x-other.getX())>Math.abs(y-other.getY())) {
            diag=true;
        }
        if(Math.abs(y-other.getY())>Math.abs(x-other.getX())) {
            diag2=true;
        }
        if(reload2<=0) {
            if(x==other.getX() && y==other.getY()) {
                np=other.getFront();
                isAngle=false;
            }
            else if(other.getFacing().equals("North")) {
                if(y>other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y<other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y>other.getY()) {
                    np=other.getFront();
                    w=1;
                }
                else if(y<other.getY()) {
                    np=other.getRear();
                    w=1;
                }
                else if(y==other.getY()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("South")) {
                if(y>other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y<other.getY() && diag==true) {
                    np=other.getSide();
                }
                else if(y>other.getY()) {
                    np=other.getRear();
                    w=1;
                }
                else if(y<other.getY()) {
                    np=other.getFront();
                    w=1;
                }
                else if(y==other.getY()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("West")) {
                w=1;
                if(x>other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x<other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x>other.getX()) {
                    np=other.getRear();
                    w=0;
                }
                else if(x<other.getX()) {
                    np=other.getFront();
                    w=0;
                }
                else if(x==other.getX()) {
                    np=other.getSide();
                }
            }
            else if(other.getFacing().equals("East")) {
                w=1;
                if(x>other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x<other.getX() && diag2==true) {
                    np=other.getSide();
                }
                else if(x>other.getX()) {
                    np=other.getFront();
                    w=0;
                }
                else if(x<other.getX()) {
                    np=other.getRear();
                    w=0;
                }
                else if(x==other.getX()) {
                    np=other.getSide();
                }
            }
            reload2=reload;
            double x=Math.random();
            /**
             * acc/(distance/10)^0.5
             */
            if(x>calcAcc(acc,getDistance(this,other))) {
                return "Miss!";
            }
            if(np==-1) {
                return "Shot Bounced off!";
            }
            if(pen==-1) {
                other.destroy();
                return "Enemy "+other.getName()+" destroyed!";
            }
            if(isAngle) {
                np=calcEffArmor(other, np, w);
            }
            if(np<=pen) {
                other.destroy();
                return "Enemy "+other.getName()+" destroyed!";
            }
            else {
                return "Shot Bounced off!";
            }
        }
        return "Reloading!";
    }
    
    /**
     * Returns distance between 2 tanks
     */
    public static double getDistance(Tank one, Tank two) {
        int x1=one.getX();
        int y1=one.getY();
        int x2=two.getX();
        int y2=two.getY();
        return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
    }
    
    /**
     * Calculates chance of hitting target
     * chance=accuracy/(d/10)^0.5
     */
    public static double calcAcc(double a, double d) {
        return a/Math.pow(d/10,0.5);
    }

    /**
     * Uses trigonomety to calculate the effective armor thickness of a tank.
     * Used by the shoot function
     */
    public int calcEffArmor(Tank other, int thickness, int which) {
        double x1=x;
        double x2=other.getX();
        double y1=y;
        double y2=other.getY();
        double angle=0;
        if(which==0 && x1!=x2) {
            angle = Math.atan(Math.abs(y1-y2)/Math.abs(x1-x2));
        }
        else if(y1!=y2){
            angle = Math.atan(Math.abs(x1-x2)/Math.abs(y1-y2));
        }
        double thick=thickness;
        thick/=Math.cos(angle);
        //System.out.println(angle+" "+thickness+"/"+Math.cos(angle)+"="+thick);
        return (int)thick;
    }

    /**
     * Kills the player
     */
    public void destroy() {
        isAlive=false;
    }

    public int getReload() {
        return reload;
    }

    public int getFront() {
        return armorFront;
    }

    public int getSide() {
        return armorSide;
    }

    public int getRear() {
        return armorRear;
    }

    public String getFacing() {
        return facing;
    }

    public void setReload(int r) {
        reload=r;
        reload2=reload;
    }

    public void reload() {
        reload2=0;
    }

    public void setFacing(String f) {
        facing=f;
    }

    public int getPen() {
        return pen;
    }

    public void doReloadP() {
        reload2--;
        if(reload2==0) {
            System.out.println("Gun loaded!");
        }
        if(reload2<0) {
            reload2=0;
        }
    }

    public void doReload() {
        reload2--;
        if(reload2<1) {
            reload2=-1;
        }
    }

    public boolean spot(Tank other, double distance) {
        if((viewRange==-1 || other.getCamo()<-1) && other.isAlive()==true) {
            return true;
        }
        if(viewRange/other.getCamo()>=distance && other.isAlive()==true) {
            return true;
        }
        return false;
    }

    /**
     * Attempts to spot tank other with a given distance
     */
    public String spotP(Tank other, double distance) {
        //If player has indefinite view range (Mwahahahahaha)
        //...or enemy has camo less than -1
        //Pretty much spots enemy automatically
        if((viewRange==-1 && other.isAlive()==true) || other.getCamo()<-1) {
            if(y<other.getY() && x<other.getX()) {
                return "Enemy "+other.getName()+" northeast!";
            }
            else if(y<other.getY() && x>other.getX()) {
                return "Enemy "+other.getName()+" northwest!";
            }
            else if(y>other.getY() && x<other.getX()) {
                return "Enemy "+other.getName()+" southeast!";
            }
            else if(y>other.getY() && x>other.getX()) {
                return "Enemy "+other.getName()+" southwest!";
            }
            else if(y>other.getY()) {
                return "Enemy " +other.getName()+" south!";
            }
            else if(y<other.getY()) {
                return "Enemy "+other.getName()+" north!";
            }
            else if(x<other.getX()) {
                return "Enemy "+other.getName()+" east!";
            }
            else if(x>other.getX()) {
                return "Enemy "+other.getName()+" west!";
            }
            else {
                return "Enemy "+other.getName()+" right next to you!";
            }
        }
        //If player can see target
        //If other player's camo is -1, they can't be seen...More Mwahahahaha.
		//Considering making it so that player camo is divided by current gear
		//That way moving faster makes you less sneaky
        if(viewRange/other.getCamo()>=distance && other.isAlive()==true) {
            if(y<other.getY() && x<other.getX()) {
                return "Enemy "+other.getName()+" northeast!";
            }
            else if(y<other.getY() && x>other.getX()) {
                return "Enemy "+other.getName()+" northwest!";
            }
            else if(y>other.getY() && x<other.getX()) {
                return "Enemy "+other.getName()+" southeast!";
            }
            else if(y>other.getY() && x>other.getX()) {
                return "Enemy "+other.getName()+" southwest!";
            }
            else if(y>other.getY()) {
                return "Enemy " +other.getName()+" south!";
            }
            else if(y<other.getY()) {
                return "Enemy "+other.getName()+" north!";
            }
            else if(x<other.getX()) {
                return "Enemy "+other.getName()+" east!";
            }
            else if(x>other.getX()) {
                return "Enemy "+other.getName()+" west!";
            }
            else {
                return "Enemy "+other.getName()+" right next to you!";
            }
        }
        return "";
    }

    public void setViewRange(int x) {
        viewRange=x;
    }

    public double getViewRange() {
        return viewRange;
    }

    public void setX(int i) {
        x=i;
    }

    public void setY(int i) {
        y=i;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
}