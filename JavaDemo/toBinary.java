import java.util.Scanner;
public class toBinary
{
    public static void main(String args[]) {
        System.out.println("Enter a number to convert to binary:");
        int y;
        Scanner input = new Scanner(System.in);
        while(true) {
            y=input.nextInt();
            System.out.println(convert(y));
        }
    }

    public static String convert(int from) {
        String s="";
        int remain=from;
        int x=0;
        int counter=0;
        while(x<from) {
            x=(int) Math.pow(2,counter);
            counter++;
        }
        counter--;
        for(int i=counter-1;i>-1;i--) {
            if(Math.pow(2,i)<=remain) {
                remain-=(int) Math.pow(2,i);
                s=s+"1";
            }
            else {
                s=s+"0";
            }
        }
        return s;
    }
}