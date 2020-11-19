import java.util.Scanner;
public class inThread implements Runnable
{
    public void run() {
        Scanner in= new Scanner(System.in);
        while(true) {
            Server.setin(in.nextLine());
        }
    }
}