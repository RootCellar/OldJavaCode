//Darian Marvel
//Sept. 13, 2016
//Finding prime numbers, the slow but easy way
import java.io.*;

public class Prime1
{
    public static void main(String args[]) throws IOException
    {
        int w=10000000;
        int[] primeArray = new int[40000000];
        //int[] l = new int[40000000];
        int z=0;
        int d=0;
        System.out.print("Starting...\n");
        for (int x=2 ; x<w ; x++) 
        {
            boolean isPrime=true;
            d=Check(x,w,d);
            for (int y=2 ; y<=Math.sqrt(x) ; y++) 
            {
                if (x%y==0) {
                    isPrime=false;
                    break;
                }
            }
            if (isPrime==true) {
                primeArray[z]=x;
                //System.out.printf("%d\n",x); //Disabled to increase program speed. Printing slows the program down.
                z++;
            }
        }
        System.out.print("Writing to file...");
        File file = new File("Primes.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file); 
        writer.write("   Write Start    ");
        String s;
        String s2;
        writer.write("(1)1");
        for (z=0 ; z<40000000 ; z++) {
            if (primeArray[z]==0) {
                continue;
            }
            s = Integer.toString(primeArray[z]);
            s2 = Integer.toString(z+2);
            writer.write(", ");
            writer.write("(" + s2 +")");
            writer.write( s );

        }
        writer.flush();
        writer.close();
        System.out.print("Finished!");
    }
    
    
    public static int Check(int c, int n, int d) {
            double r=c;
            double t=n;
            double a=(r/t)*100;
            int b=(int) a;
            if (b!=d) {
                System.out.printf("%d%% Done\n",b);
                d=b;
            }
            return d;
    }
}