import java.util.Scanner;
import java.util.Random;
public class Game {
    static private String in;
    public static void main(String args[]) {
        System.out.println("Starting...");
        inThread t = new inThread();
        Thread t2=new Thread(t);
        t2.start();
        String in2="";
        boolean done=false;
        int minLoc;
        double min;
        /**
         * Tank Set up
         * (String name, int armor front, int armor side, int armor rear, int reload (in ticks), int penetration, String facing direction, double view range, double camo, String AiType)
		 * Preset tanks below
		 * You can use the tank preset in the constructor instead of the details to make tanks easier
         */
        Tank Maus = new Tank("Maus",300,185,160,50,200,"East",14,0.8,"SuperHeavy");
        Tank SuperMaus = new Tank("SuperMaus",1000,1000,1000,0,1000,"North",-1,0,"CantDie");
        Tank E100 = new Tank("E 100",300,160,120,40,220,"East",15,1,"SuperHeavy");
        Tank M4Sherman = new Tank("M4 Sherman",48,20,20,20,80,"North",10,1.5,"Medium");
        Tank T110E5 = new Tank("T110E5",250,80,30,35,185,"West",18,1.2,"Heavy");
		Tank IS7 = new Tank("IS7",400,120,80,40,170,"East",18,1.1,"Heavy");
		Tank Type4 = new Tank("Type 4 Heavy",250,150,150,70,200,"East",16,0.5,"SuperHeavy");

        //Tank creation

        Tank player = new Tank(Type4);
        player.setType("Player");
        Tank[] enemy = new Tank[4];
        Tank[] ally = new Tank[4];
        for(int i=0; i<4; i++) {
            ally[i] = new Tank(M4Sherman);
            ally[i].setType("Ally");
        }

        for(int i=0; i<4; i++) {
            enemy[i] = new Tank(T110E5);
            enemy[i].setType("Enemy");
        }
        enemy[1].setX(1);
        enemy[2].setX(2);
        enemy[3].setX(3);

        //End tank creation

        int spot=0;
        System.out.println(player);
        while(player.isAlive()) {
            try{
                Thread.sleep(100);
            } catch(InterruptedException ex) {

            }
            in2=upToSpace(in);
            in=subUpToSpace(in);
            player.doReloadP();
            for(int i=0; i<enemy.length; i++) {
                enemy[i].doReload();
            }
            for(int i=0; i<ally.length; i++) {
                ally[i].doReload();
            }
            if(in2.equals("w ")) {
                player.setY(player.getY()+1);
            }
            if(in2.equals("s ")) {
                player.setY(player.getY()-1);
            }
            if(in2.equals("a ")) {
                player.setX(player.getX()-1);
            }
            if(in2.equals("d ")) {
                player.setX(player.getX()+1);
            }
            if(in2.equals("shoot ")) {
                min=0;
                for(int i=0; i<enemy.length; i++) {
                    if(enemy[i].isAlive()==true && player.spot(enemy[i],getDistance(player,enemy[i]))==true) {
                        min=getDistance(player,enemy[i]);
                    }
                }
                minLoc=-1;
                for(int i=0; i<enemy.length; i++) {
                    if(getDistance(player,enemy[i])<=min && player.spot(enemy[i],getDistance(player,enemy[i]))==true && enemy[i].isAlive()==true) {
                        min=getDistance(player,enemy[i]);
                        minLoc=i;
                    }
                }
                if(minLoc!=-1) {
                    player.shootP(enemy[minLoc]);
                }
                else {
                    System.out.println("No target!");
                }
            }
            if(in2.equals("fw ")) {
                player.setFacing("North");
                System.out.println("Tank turned to face north!");
            }
            if(in2.equals("fa ")) {
                player.setFacing("West");
                System.out.println("Tank turned to face west!");
            }
            if(in2.equals("fs ")) {
                player.setFacing("South");
                System.out.println("Tank turned to face south!");
            }
            if(in2.equals("fd ")) {
                player.setFacing("East");
                System.out.println("Tank turned to face east!");
            }
            if(in2.equals("living ")) { //Counts living allies/enemies
                minLoc=0;
                for(int i=0; i<enemy.length; i++) {
                    if(enemy[i].isAlive()==true) {
                        minLoc++;
                    }
                }
                System.out.println(minLoc+" enemies left");
                minLoc=0;
                for(int i=0; i<ally.length; i++) {
                    if(ally[i].isAlive()==true) {
                        minLoc++;
                    }
                }
                System.out.println(minLoc+" allies left");
                minLoc=0;
            }
            if(in2.equals("print ")) {
                System.out.println(player);
            }
            if(in2.equals("spot ")) {
                spot=0;
            }

            //Enemy AI
            for(int i=0; i<enemy.length; i++) {
                minLoc=-2;
                min=0;
                if(enemy[i].spot(player,getDistance(player,enemy[i]))==true && enemy[i].isAlive()==true) { //Accept player as valid target, if possible
                    min=getDistance(player,enemy[i]);
                    minLoc=-1;
                }

                for(int w=0; w<ally.length; w++) { //Find 1st allied target, if they are closer than the player
                    if(getDistance(enemy[i],ally[w])<=min && enemy[i].spot(ally[w],getDistance(enemy[i],ally[w]))==true && ally[w].isAlive()==true && enemy[i].isAlive()==true) {
                        min=getDistance(enemy[i],ally[w]);
                    }
                }

                for(int w=0; w<ally.length; w++) { //Find better target
                    if(getDistance(enemy[i],ally[w])<=min && enemy[i].spot(ally[w],getDistance(enemy[i],ally[w]))==true && ally[w].isAlive()==true && enemy[w].isAlive()==true) {
                        min=getDistance(enemy[i],ally[w]);
                        minLoc=w;
                    }
                }
                if(minLoc==-1) {
                    enemy[i].shoot(player);
                }
                else if(minLoc!=-2) {
                    enemy[i].shoot(ally[minLoc]);
                }
            }
            //End Enemy AI

            //Ally AI
            for(int i=0; i<ally.length; i++) {
                minLoc=-1; //Assume that there is no target
                min=0;
                for(int w=0; w<enemy.length; w++) { //Find 1st valid target (minLoc stays -1 if none are found)
                    if(ally[i].isAlive()==true && enemy[w].isAlive()==true && ally[i].spot(enemy[w],getDistance(ally[i],enemy[w]))==true) {
                        min=getDistance(ally[i],enemy[w]);
                    }
                }

                for(int w=0; w<enemy.length; w++) { //Find closer target
                    if(ally[i].isAlive()==true && getDistance(ally[i],enemy[w])<=min && ally[i].spot(enemy[w],getDistance(ally[i],enemy[w]))==true && enemy[w].isAlive()==true) {
                        min=getDistance(ally[i],enemy[w]);
                        minLoc=w;
                    }
                }

                if(minLoc!=-1) { //Shoot the target, if there is one
                    ally[i].shoot(enemy[minLoc]);
                }
            }
            //End Ally AI

            //Player Spotting
            spot--;
            if(spot<=0) {
                for(int i=0; i<4; i++) {
                    player.spotP(enemy[i],getDistance(player,enemy[i]));
                }
                spot=40;
            }
        }
    }

    public static double getDistance(Tank one, Tank two) {
        int x1=one.getX();
        int y1=one.getY();
        int x2=two.getX();
        int y2=two.getY();
        return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
    }

    public static String subUpToSpace(String s) {
        String s2="";
        int x=0;
        if(s==null) {
            return "";
        }
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
        if(s==null) {
            return "";
        }
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

    public static void addin(String n) {
        in+=n;
    }
}