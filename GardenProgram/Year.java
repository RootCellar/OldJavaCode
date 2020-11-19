import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
public class Year {
    private int year;
    private ArrayList<Day> days;
    public Year(int y) {
        year=y;
        days=new ArrayList<Day>();
        readYear();
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void newDay(int m, int d, int h, int l, String w) {
        if(isADay(m,d)!=-1) {
            days.set(isADay(m,d),new Day(m,d,h,l,w));
        }
        days.add(new Day(m,d,h,l,w));
    }

    public int isADay(int m, int d) {
        for(int i=0; i<days.size(); i++) {
            if(days.get(i).getMonth()==m && days.get(i).getDay()==d) {
                return i;
            }
        }
        return -1;
    }

    public void readYear() {
        try{
            File folder = new File("Years");
            folder.mkdir();
            File from = new File("Years/"+Integer.toString(year)+".txt");
            System.out.println("Reading from "+Integer.toString(year)+".txt");
            for(int i=0; i<days.size(); i++) {
                days.remove(i);
            }
            int m=0;
            int d=0;
            int l=0;
            int h=0;
            String w="";
            if(from.canRead()==false) {
                try{
                    from.createNewFile();
                }catch(IOException e) {
                    System.out.println("Error making new year file");
                }
            }
            try{
                Scanner in = new Scanner(from);
                String read;
                while(in.hasNext()) {
                    read=in.next();
                    if(read.equals("day:")) {
                        m=Integer.parseInt(in.next());
                        d=Integer.parseInt(in.next());
                        h=Integer.parseInt(in.next());
                        l=Integer.parseInt(in.next());
                        w=in.next();
                        days.add(new Day(m,d,h,l,w));
                        in.next();
                    }
                }
                in.close();
            }catch(FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("\n\nThat was a wierd error");
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("\n\nError when reading new year");
        }
    }

    public void writeYear() {
        try{
            File folder = new File("Years");
            folder.mkdir();
            File to = new File("Years/"+Integer.toString(year)+".txt");
            FileWriter outf = new FileWriter(to);
            for(int i=0; i<days.size(); i++) {
                outf.write("day: ");
                outf.write(Integer.toString(days.get(i).getMonth())+" ");
                outf.write(Integer.toString(days.get(i).getDay())+" ");
                outf.write(Integer.toString(days.get(i).getHigh())+" ");
                outf.write(Integer.toString(days.get(i).getLow())+" ");
                outf.write(days.get(i).getWeather()+" ");
                outf.write("ENDDAY ");
                outf.flush();
            }
            outf.close();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("\n\nAn error ocurred");
        }
    }
    
    public String toString() {
        String r=Integer.toString(year)+"\n";
        for(int i=0; i<days.size(); i++) {
            r+=Integer.toString(days.get(i).getMonth())+"/";
            r+=Integer.toString(days.get(i).getDay())+"/";
            r+=Integer.toString(year)+"\n";
            r+="High: "+Integer.toString(days.get(i).getHigh())+"\n";
            r+="Low: "+Integer.toString(days.get(i).getLow())+"\n";
            r+="Weather: "+days.get(i).getWeather()+"\n";
        }
        
        return r;
    }
}