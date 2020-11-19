//Darian Marvel
//Sept. 13, 2016
//Finding prime numbers, the slow but easy way
import java.io.*;

public class AddPrimes
{
    public static void main(String args[]) throws IOException
    {
        int w=2000000;
        int[] primeArray = new int[w];
        int z=0;
        int a=0;
        System.out.print("Starting...\n");
        for (int x=2 ; x<w ; x++) 
        {
            boolean isPrime=true;
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
        for (z=0 ; z<w ; z++) {
            if (primeArray[z]==0) {
                continue;
            }
            s = Integer.toString(primeArray[z]);
            s2 = Integer.toString(z+2);
            writer.write(", ");
            writer.write("(" + s2 +")");
            writer.write( s );
            a=a+primeArray[z];

        }
        writer.flush();
        writer.close();
        System.out.print("Finished!");
        System.out.printf("Sum of all primes: %d",a);
    }
}