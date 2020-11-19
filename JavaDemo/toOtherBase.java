import java.util.Scanner;
public class toOtherBase
{
    public static void main(String args[]) {
        System.out.println("Enter a number to convert to another base:");
        int y;
        int base=2;
        Scanner input = new Scanner(System.in);
        while(true) {
            y=input.nextInt();
            base=input.nextInt();
            System.out.println(convert(y,base));
        }
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
                s=s+z;
            }
            else {
                s=s+"0";
            }
        }
        return s;
    }
}