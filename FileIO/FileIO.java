import java.io.*;
import java.util.Scanner;
public class FileIO {
    public static void main(String args[]) throws IOException {
        File f=new File("Stuff.txt");
        f.createNewFile();
        Scanner inf = new Scanner(f);
        String in="";
        int in2;
        /**
        FileWriter outf=new FileWriter(f);
        outf.write("Hello!");
        outf.flush();
         */
        while(inf.hasNextLine()) {
            in=inf.next(); 
            System.out.println( in );
            //Grab Stats
            System.out.println("Type: "+inf.next());
            System.out.println("Armor (Front): "+inf.nextInt());
            System.out.println("Armor (Side): "+inf.nextInt());
            System.out.println("Armor (Rear): "+inf.nextInt());
        }
        inf.close();
    }
}