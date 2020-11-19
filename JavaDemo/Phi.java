//Darian Marvel
//Sept. 6, 2016
//Calculate the fibonacci sequence

import java.util.Scanner;

public class Phi
{
    public static void main(String args[]) {
        //Variable initialization
        long fibOne=1;
        long fibTwo=0;
        long fibThree=0;
        int Num=28;
        double a;
        double b;
        double phi;
        //Prints the first number, because the function below does not.
        for ( float x=1; x<Num  ; x++ ) {
            //Adds the number and the previous one
            fibThree=fibOne+fibTwo;
            a=fibOne;
            b=fibTwo;
            phi=a/b;
            System.out.println(phi);
            //Moves the variables forward in the sequence
            fibTwo=fibOne;
            fibOne=fibThree;
            
        }
    }

}