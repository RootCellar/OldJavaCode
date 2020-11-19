//Darian Marvel
//Sept. 6, 2016
//Calculate the fibonacci sequence

import java.util.Scanner;

public class Fibonacci
{
    public static void main(String args[]) {
        //Variable initialization
        Scanner scanny = new Scanner(System.in);
        int fibOne=1;
        int fibTwo=0;
        int fibThree=0;
        float y;
        int Num;
        System.out.print("Because of limits and the size of the numbers\nin the sequence, the limit is 46.\n");
        System.out.print("How far into the sequence should we go?:");
        Num = scanny.nextInt();
        if (Num>46) {
            System.out.printf("\nYou entered %d, but this program can only go to 46.\n",Num);
            Num=46;
        }
        System.out.print("\n\n1");
        //Prints the first number, because the function below does not.
        for ( float x=1; x<Num  ; x++ ) {

            try {
                Thread.sleep(1);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            //Adds the number and the previous one
            fibThree=fibOne+fibTwo;
            y=x/10;

            //Debug Statements
            //System.out.printf(" Debug: y = %f",y);
            //System.out.printf(" Debug: Math.round(y) = %d",Math.round(y));

            //Moves the variables forward in the sequence
            fibTwo=fibOne;
            fibOne=fibThree;
            //Prints the result
            if ( y == Math.round(y) ) {
                System.out.printf(",\n%d",fibThree);
            }
            else {
                System.out.printf(", %d",fibThree);
            }
        }
    }

}