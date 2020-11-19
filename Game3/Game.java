import java.util.Scanner;
import java.util.Random;
public class Game {
    static private String in2;
    public static void main(String args[]) {
        System.out.println("Starting...");
        Entity player = new Entity();
        System.out.println();
        Random rand = new Random();
        boolean done=false;
        player.setXPMult(3);
        player.setLvlMult(1.1);
        /**
         * Warrior: 30, 10, 10
         * Sentinal: 50, 7, 20
         * Mage: 15, 25, 5
         * Super: 1000, 1000, 1000
         */
        int in;
        Scanner input = new Scanner(System.in);
        player.setBot(false);
        player.setType("Player");
        //System.out.println(player);
        System.out.println("Welcome!");
        System.out.println("Will you....");
        System.out.println("1. Load");
        System.out.println("2. Make new character");
        while(done==false) {
            in2=input.next();
            if(in2.equals("1")) {
                System.out.println("What is the level code?: ");
                String xpCode=input.next();
                int xp=toBase10(xpCode);
                player.setXp(xp);
                System.out.println("What is the class code?: ");
                String pClass = input.next();
                if(pClass.equals("WAR")) {
                    player.setBaseHealth(30);
                    player.setBaseAttack(10);
                    player.setBaseDefense(10);
                    player.calc();
                }
                else if(pClass.equals("SEN")) {
                    player.setBaseHealth(50);
                    player.setBaseAttack(7);
                    player.setBaseDefense(20);
                    player.calc();
                }
                else if(pClass.equals("MAG")) {
                    player.setBaseHealth(15);
                    player.setBaseAttack(25);
                    player.setBaseDefense(5);
                    player.calc();
                }
                else if(pClass.equals("SPE")) {
                    player.setBaseHealth(100);
                    player.setBaseAttack(100);
                    player.setBaseDefense(100);
                    player.calc();
                }
                else {
                    System.out.println("Invalid class!");
                    return;
                }
                player.setHealth( player.getMaxHealth() );
                System.out.println(player);
                done=true;
            }

            if(in2.equals("2")) {
                System.out.println("Which Class?");
                System.out.println("1. Warrior");
                System.out.println("2. Mage");
                System.out.println("3. Sentinal");
                in2=input.next();
                player.setXp(0);
                if(in2.equals("1")) {
                    player.setBaseHealth(30);
                    player.setBaseAttack(10);
                    player.setBaseDefense(10);
                    player.calc();
                }
                else if(in2.equals("2")) {
                    player.setBaseHealth(15);
                    player.setBaseAttack(25);
                    player.setBaseDefense(5);
                    player.calc();
                }
                else if(in2.equals("3")) {
                    player.setBaseHealth(50);
                    player.setBaseAttack(7);
                    player.setBaseDefense(20);
                    player.calc();
                }
                else {
                    System.out.println("Invalid class!");
                    return;
                }
                player.setHealth( player.getMaxHealth() );
                System.out.println(player);
                done=true;
            }
        }
        inThread t = new inThread();
        Thread t2=new Thread(t);
        t2.start();
        done=false;
        String in3;
        while(!done) {
            try{
                Thread.sleep(100);
            } catch(InterruptedException ex) {

            }
            in3=upToSpace(in2);
            in2=subUpToSpace(in2);
            
        }
    }

    public static String subUpToSpace(String s) {
        String s2="";
        int x=0;
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return "";
            }
        }

        return s.substring(x,s.length()); 
    }

    public static String upToSpace(String s) {
        String s2="";
        int x=0;
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return s;
            }
        }

        return s.substring(0,x);
    }

    public static int CalcDamage(int damage, int armor) {
        double a=damage;
        double b=armor;
        double c=a/b;
        double finalDamage;
        int finalDamage2;
        if(a>b) {
            c=c-1;
            c=c/3.0;
            c=c+1;
        }
        if(c<=0.1) {
            return 0;
        }
        else {
            finalDamage=a*c;
            finalDamage2=(int) finalDamage;
            return finalDamage2;
        }
    }

    public static int toBase10(String x) {
        int total=0;
        int count=0;
        int w;
        String y;
        for(int c=x.length()-1;c>-1;c--) {
            y=x.substring(c,c+1);
            if(y.equals("A")) {
                y="10";
            }
            if(y.equals("B")) {
                y="11";
            }
            if(y.equals("C")) {
                y="12";
            }
            if(y.equals("D")) {
                y="13";
            }
            if(y.equals("E")) {
                y="14";
            }
            if(y.equals("F")) {
                y="15";
            }
            w=Integer.parseInt(y);
            total+=w*Math.pow(16,count);
            count++;
        }
        return total;
    }

    public static String toBase16(int from,int base) {
        String s="";
        int remain=from;
        int x=0;
        int counter=0;
        int z;
        int a;
        while(x<=from) {
            x=(int) Math.pow(base,counter);
            counter++;
        }
        counter--;
        for(int i=counter;i>-1;i--) {
            if(Math.pow(base,i)<=remain) {
                a=(int) Math.pow(base,i);
                z=(int) remain/a;
                remain-=(int) z*Math.pow(base,i);
                if(z==10) {
                    s+="A";
                }
                else if(z==11) {
                    s+="B";
                }
                else if(z==12) {
                    s+="C";
                }
                else if(z==13) {
                    s+="D";
                }
                else if(z==14) {
                    s+="E";
                }
                else if(z==15) {
                    s+="F";
                }
                else {
                    s=s+z;
                }
            }
            else {
                s=s+"0";
            }
        }
        return s;
    }
    
    public static void addin2(String n) {
        in2+=n;
    }
}