import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
public class GardenProgram
{
    public static void main(String args[]) {
        Scanner scanny=new Scanner(System.in);
        ArrayList<Year> years=new ArrayList<Year>();
        ArrayList<Plant> plants=new ArrayList<Plant>();
        File y;
        for(int i=2016;i<3000;i++) { //Set up years
            y=new File("Years/"+Integer.toString(i)+".txt");
            if(y.canRead()==true) {
                years.add(new Year(i));
            }
        }

        for(int i=0; i<years.size(); i++) { //Print years
            System.out.println(years.get(i));
        }

        for(int i=0;i<100;i++) { //Set up plants
            y=new File("Plants/"+Integer.toString(i)+".txt");
            if(y.canRead()==true) {
                plants.add(new Plant(i));
            }
        }

        for(int i=0; i<plants.size(); i++) { //Print plants
            System.out.println(plants.get(i));
        }

        boolean done=false;
        String read="";
        while(!done) {
            System.out.println("\n\nGarden Program");
            System.out.println("Commands: ");
            System.out.println("/today - View today's info");
            System.out.println("/week - View this week's info");
            read=scanny.next();
            System.out.println();
            if(read.equals("/today")) {
                for(int i=0; i<plants.size(); i++) {
                    read=plants.get(i).getWater();
                    if(read.equals("Daily") && plants.get(i).isPlanted()==true) {
                        System.out.println("Did you water your "+plants.get(i).getName()+" today?");
                    }
                }
            }

            if(read.equals("/week")) {
                for(int i=0; i<plants.size(); i++) {
                    read=plants.get(i).getWater();
                    if(read.equals("Weekly")&& plants.get(i).isPlanted()==true) {
                        System.out.println("Did you water your "+plants.get(i).getName()+" this week?");
                    }
                }
            }

            if(read.equals("/whenplant")) {
                for(int i=0; i<plants.size(); i++) {

                }
            }
        }
    }

    public static double[][] getHighs(ArrayList<Year> y) {
        double highs[][]=new double[12][31];
        double howMany=0;
        for(int i=0; i<12; i++) {
            for(int w=0; w<31; w++) {
                howMany=0;
                highs[i][w]=1000;
                for(int x=0; x<y.size(); x++) {
                    if(y.get(i).isADay(i,w)!=-1) {
                        highs[i][w]=0;
                    }
                }
                for(int x=0; x<y.size(); x++) {
                    if(y.get(i).isADay(i,w)!=-1) {
                        highs[i][w]+=y.get(i).getDays().get(y.get(i).isADay(i,w)).getHigh();
                        howMany++;
                    }
                }
                if(howMany>0) {
                    highs[i][w]/=howMany;
                }
            }
        }
        return highs;
    }
    
    public static double[][] getLows(ArrayList<Year> y) {
        double lows[][]=new double[12][31];
        double howMany=0;
        for(int i=0; i<12; i++) {
            for(int w=0; w<31; w++) {
                howMany=0;
                lows[i][w]=1000;
                for(int x=0; x<y.size(); x++) {
                    if(y.get(i).isADay(i,w)!=-1) {
                        lows[i][w]=0;
                    }
                }
                for(int x=0; x<y.size(); x++) {
                    if(y.get(i).isADay(i,w)!=-1) {
                        lows[i][w]+=y.get(i).getDays().get(y.get(i).isADay(i,w)).getLow();
                        howMany++;
                    }
                }
                if(howMany>0) {
                    lows[i][w]/=howMany;
                }
            }
        }
        return lows;
    }
}
