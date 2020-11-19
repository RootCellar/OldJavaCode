//Darian Marvel
//Sept. 20, 2016
//Finding a 1000 digit fibonacci number

import java.io.*;

public class Digit1000Fib
{
    public static void main(String args[]) throws IOException {
        File file = new File("Fibonacci.txt");
        int h = 100;
        System.out.print("Starting...\n");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        int[] fibOne= new int[h];
        int[] fibTwo= new int[h];
        int[] fibThree= new int[h];
        fibOne[0]=1;
        boolean found=false;
        int x=1;
        while (found==false) {
            for (int i=0; i<h; i++) {
                fibThree[i]=fibOne[i]+fibTwo[i];
            }
            for (int i=0; i<h; i++) {
                if (fibThree[i]>9) {
                    fibThree[i]=fibThree[i]-10;
                    fibThree[i+1]=fibThree[i+1]+1;
                }
            }
            for (int j=0; j<h; j++) {
                fibTwo[j]=fibOne[j];
                fibOne[j]=fibThree[j];
            }
            if (fibThree[h-1]!=0) {
                found=true;
            }
            x=x+1;
        }
        String s;
        for (int i=h-1; i>=0; i--) {
            s=Integer.toString(fibThree[i]);
            writer.write(s);
        }
        writer.flush();
        writer.close();
        System.out.printf("X = %d\n",x);
    }
}
