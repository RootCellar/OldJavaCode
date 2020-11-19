package com.Darian.RPG;
import java.util.ArrayList;
public class Character
{
    private String name;
    private int xp;
    private int level;
    private int x=-1;
    private int y=-1;
    private double hp;
    private double mana;
    private double mmana;
    private double mHp;
    private double atk;
    private double def;
    private double matk;
    private double mult;
    private double regenrate;
    private Class type;
    private Race race;
    private boolean isAlive;
    private boolean isImmortal=false;
    private boolean isInvincible=false;
    private static int idPoint=0;
    private static ArrayList<Character> chars=new ArrayList<Character>();
    private ArrayList<Character> alerts = new ArrayList<Character>();
    private int id=0;
    private static boolean findId=false;
    public Character(String n,Class c,Race r,double m) {
        name=n;
        type=c;
        mult=m;
        race=r;
        setRegenRate(0.001);
        calcStats();
        if(findId) {
            id=idPoint;
            chars.add(this);
            findNewId();
        }
    }
    
    public int getXpOnDeath() {
        return level;
    }

    public void findValidId(boolean b) {
        findId=b;
    }

    public void remove() {
        for(int i=0; i<chars.size(); i++) {
            if(chars.get(i).equals(this)) {
                chars.remove(i);
            }
        }
    }

    public void alert(Character a) {
        alerts.add(a);
    }

    public void setRace(Race to) {
        race=to;
    }

    public static String getList() {
        String ret="";
        for(int i=0; i<chars.size(); i++) {
            ret+=chars.get(i).getName()+" "+chars.get(i).getId()+" "+chars.get(i).getXP()+"\n";
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public boolean nameIsUsed(String n) {
        for(int i=0; i<chars.size(); i++) {
            if(n.equals(chars.get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Character other) {
        if(id==other.getId()) {
            return true;
        }
        return false;
    }

    public void findNewId() {
        if(!findId) {
            return;
        }
        for(int i=id; i<100000000; i++) {
            boolean avail=true;
            for(int j=0; j<chars.size(); j++) {
                if(chars.get(j).getId()==i) {
                    avail=false;
                }
            }
            if(avail) {
                idPoint=i;
                return;
            }
        }
        for(int i=0; i<100000000; i++) {
            boolean avail=true;
            for(int j=0; j<chars.size(); j++) {
                if(chars.get(j).getId()==i) {
                    avail=false;
                }
            }
            if(avail) {
                idPoint=i;
                return;
            }
        }
        System.out.println("Could not find new id point");
    }
    
    public void setId(int i) {
        id=i;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addX(int x2) {
        x+=x2;
    }

    public void addY(int y2) {
        y+=y2;
    }

    public void setX(int x2) {
        x=x2;
    }

    public void setY(int y2) {
        y=y2;
    }

    public int getLevel() {
        calcStats();
        return level;
    }

    public int getXP() {
        return xp;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isImmortal() {
        return isImmortal;
    }

    public void invincible(boolean i) {
        isInvincible=i;
    }

    public void immortal(boolean i) {
        isImmortal=i;
        if(i) {
            heal();
            setMana(mmana);
        }
    }

    public void regen() {
        double heal=mHp*regenrate;
        heal*=race.getRegenMult();
        heal(heal);
    }

    public String toString() {
        return "Name: "+name+"\n"+
        "XP: "+xp+"\n"+
        "Level: "+level+"\n\n"+
        "Race: \n"+race+"\n"+
        "Class: \n"+type+"\n"+
        "\nHp: "+hp+"\n"+
        "Mana: "+mana+"\n"+
        "Max Mana: "+mmana+"\n"+
        "Max hp: "+mHp+"\n"+
        "Attack: "+atk+"\n"+
        "Defense: "+def+"\n"+
        "Magic Attack: "+matk+"\n"+
        "Mult: "+mult+"\n"+
        "Regen Rate: "+regenrate*race.getRegenMult()+"\n"+
        "Is Alive: "+isAlive+"\n"+
        "Is Invincible: "+isInvincible+"\n"+
        "Is Immortal: "+isImmortal+"\n";
    }

    public Class getType() {
        return type;
    }

    public Race getRace() {
        return race;
    }

    public double getHp() {
        return hp;
    }

    public double getMana() {
        return mana;
    }

    public void subMana(double m) {
        if(!isImmortal) {
            mana-=m;
            if(mana<0) {
                mana=0;
            }
        }
    }

    public void regenMana(double a) {
        mana+=a;
        if(mana>mmana) {
            mana=mmana;
        }
    }

    public void regenMana() {
        regenMana(mmana/30);
    }

    public void setMana(double a) {
        mana=a;
        if(mana>mmana) {
            mana=mmana;
        }
    }

    public double getMaxMana() {
        return mmana;
    }

    public double getMaxHp() {
        return mHp;   
    }

    public double getAtk() {
        return atk;
    }

    public double getDef() {
        return def;
    }

    public double getMAtk() {
        return matk;
    }

    public double getRegenRate() {
        return regenrate;
    }

    public void setRegenRate(double r) {
        regenrate=r;
    }

    public void heal(double n) {
        hp+=n;
        if(hp>mHp) {
            hp=mHp;
        }
    }

    public void heal() {
        hp=mHp;
    }

    public void damage(double n) {
        if(!isInvincible && !isImmortal) {
            hp-=n;
            if(hp<=0) {
                hp=0;
                die();
            }
        }
    }

    public void die() {
        if(!isInvincible && !isImmortal) {
            isAlive=false;
            hp=0;
            mana=0;
        }
    }

    public void revive() {
        isAlive=true;
        hp=mHp;
        mana=mmana;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setClass(Class t) {
        type=t;
    }

    public void setXp(int x) {
        xp=x;
        checkXp();
    }

    public void addXp(int x) {
        xp+=x;
        checkXp();
    }

    public void calcStats() {
        try{
            calcLevel();
            calcAtk();
            calcDef();
            calcMAtk();
            calcHp();
            calcMana();
        }catch(Exception e) {

        }
    }

    public void calcMana() {
        mmana=type.getBMana()*Math.pow(mult,level);
        mmana*=race.getManaMult();
    }

    public void calcHp() {
        mHp=type.getBHp()*Math.pow(mult,level);
        mHp*=race.getHpMult();
    }

    public void calcAtk() {
        atk=type.getBAtk()*Math.pow(mult,level);
        atk*=race.getAtkMult();
    }

    public void calcDef() {
        def=type.getBDef()*Math.pow(mult,level);
        def*=race.getDefMult();
    }

    public void calcMAtk() {
        matk=type.getBMatk()*Math.pow(mult,level);
        matk*=race.getMAtkMult();
    }

    public void calcLevel() {
        int lvl = (int)Math.sqrt(xp);
        if(lvl<1) {
            lvl=1;
        }
        level=lvl;
        checkXp();
    }

    public void checkXp() {
        if(xp>1000000000) {
            xp=1000000000;
        }
    }
}