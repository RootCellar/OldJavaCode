import java.io.*;
import java.util.Scanner;

public class FileIO
{
    public static void main(String args[]) throws IOException {
        File f = new File("test.txt");
        if(f.canRead()==false) {
            f.createNewFile();
        }
        Scanner inf;
        try{
            inf = new Scanner(f);
        }catch(FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        FileWriter outf=new FileWriter(f,true);
        /*
        outf.write("SKIP");
        inf.next();
        */
        String in;
        while(inf.hasNext()) {
            in=inf.next();
            System.out.println(in);
            System.out.println(toBase10(in));
        }
        outf.close();
        inf.close();
    }

    public static String convert(int from,int base) {
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
}