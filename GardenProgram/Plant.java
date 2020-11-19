import java.util.Scanner;
import java.io.*;
public class Plant {
    private String name;
    private int highest;
    private int lowest;
    private String water;
    private boolean isPlanted;
    public Plant(String n,int h, int l, String w, boolean b) {
        name=n;
        highest=h;
        lowest=l;
        water=w;
        isPlanted=b;
    }
    
    public void plant() {
        isPlanted=true;
    }
    
    public void harvest() {
        isPlanted=false;
    }
    
    public boolean isPlanted() {
        return isPlanted;
    }
    
    public String toString() {
        return name+":\n"+"Highest: "+highest+"\nLowest: "+lowest+"\nWater: "+water;
     }
    
    public Plant(int i) {
        readPlant(i);
    }

    public void writePlant(int i) {
        try{
            File folder = new File("Plants");
            folder.mkdir();
            File to = new File("Plants/"+Integer.toString(i)+".txt");
            FileWriter outf = new FileWriter(to);
            outf.write("plant: ");
            outf.write(name+" ");
            outf.write(Integer.toString(highest)+" ");
            outf.write(Integer.toString(lowest)+" ");
            outf.write(water+" ");
            outf.write(isPlanted+" ");
            outf.write("ENDPLANT");
            outf.flush();
            outf.close();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("\n\nAn error ocurred");
        }
    }

    public void readPlant(int i) {
        try{
            File folder = new File("Plants");
            folder.mkdir();
            File from = new File("Plants/"+Integer.toString(i)+".txt");
            System.out.println("Reading from "+Integer.toString(i)+".txt");
            if(from.canRead()==false) {
                from.createNewFile();
            }
            Scanner in=new Scanner(from);
            String read="";
            read=in.next();
            if(read.equals("plant:")) {
                name=in.next();
                highest=Integer.parseInt(in.next());
                lowest=Integer.parseInt(in.next());
                water=in.next();
                isPlanted=in.nextBoolean();
                in.next();
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("\n\nError when reading new plant");
        }
    }

    public int getHighest() {
        return highest;
    }

    public int getLowest() {
        return lowest;
    }

    public String getWater() {
        return water;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name=n;
    }

    public void setWater(String w) {
        water=w;
    }

    public void setHighest(int h) {
        highest=h;
    }

    public void setLowest(int l) {
        lowest=l;
    }
}