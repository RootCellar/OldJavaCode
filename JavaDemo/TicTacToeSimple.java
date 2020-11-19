//Darian Marvel
//Making a game, practicing functions and such.

import java.util.Scanner;
import java.util.Random;

public class TicTacToeSimple
{
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String c[] = new String[9];
        c[0]="1";
        c[1]="2";
        c[2]="3";
        c[3]="4";
        c[4]="5";
        c[5]="6";
        c[6]="7";
        c[7]="8";
        c[8]="9";
        boolean went;
        int s;
        int y;
        boolean over=false;
        while (true) {
            Display(c);
            System.out.print("Your go. Type the number of where you want to go.\n");
            went = false;
            while(went==false) {
                s = input.nextInt();
                if (c[s-1]!="X" && c[s-1]!="O") {
                    c[s-1]="X";
                    went=true;
                }
                else {
                    System.out.print("Invalid move.\n");
                }
            }
            over=winCon(c);
            if (over==true) {
                Display(c);
                System.out.print("Game over");
                break;
            }
            System.out.print("Thinking...\n");
            y=Ai(c);
            c[y]="O";
            over=winCon(c);
            if (over==true) {
                Display(c);
                System.out.print("Game over");
                break;
            }
        }
    }

    public static void Display(String c[]) {
        System.out.printf("%s | %s | %s\n",c[0],c[1],c[2]);
        System.out.printf("------------\n");
        System.out.printf("%s | %s | %s\n",c[3],c[4],c[5]);
        System.out.printf("------------\n");
        System.out.printf("%s | %s | %s\n\n\n",c[6],c[7],c[8]);
        return;
    }

    public static int Ai(String c[]) {
        int y=0;
        Random rand=new Random();
        boolean went=false;
        while (went==false) {
            int x=rand.nextInt(9);
            if (checkUsed(x,c)==false) {
                went=true;
                y=x;
            }
        }
        return y;
    }

    public static boolean checkUsed(int x,String c[]) {
        boolean b=true;
        if (c[x]!="X" && c[x]!="O") {
            b=false;
        }
        return b;
    }
    
    public static boolean winCon(String c[]) {
        //Check for X's win
        boolean won=false;
        //Horizontal checks
        if (c[0]=="X" && c[1]=="X" && c[2]=="X") {
            won=true;
        }
        if (c[3]=="X" && c[4]=="X" && c[5]=="X") {
            won=true;
        }
        if (c[6]=="X" && c[7]=="X" && c[8]=="X") {
            won=true;
        }
        //Vertical checks
        if (c[0]=="X" && c[3]=="X" && c[6]=="X") {
            won=true;
        }
        if (c[1]=="X" && c[4]=="X" && c[7]=="X") {
            won=true;
        }
        if (c[2]=="X" && c[5]=="X" && c[8]=="X") {
            won=true;
        }
        //Diagonal Checks
        if (c[0]=="X" && c[4]=="X" && c[8]=="X") {
            won=true;
        }
        if (c[2]=="X" && c[4]=="X" && c[6]=="X") {
            won=true;
        }
        //Check for O's win
        //Horizontal checks
        if (c[0]=="O" && c[1]=="O" && c[2]=="O") {
            won=true;
        }
        if (c[3]=="O" && c[4]=="O" && c[5]=="O") {
            won=true;
        }
        if (c[6]=="O" && c[7]=="O" && c[8]=="O") {
            won=true;
        }
        //Vertical checks
        if (c[0]=="O" && c[3]=="O" && c[6]=="O") {
            won=true;
        }
        if (c[1]=="O" && c[4]=="O" && c[7]=="O") {
            won=true;
        }
        if (c[2]=="O" && c[5]=="O" && c[8]=="O") {
            won=true;
        }
        //Diagonal Checks
        if (c[0]=="O" && c[4]=="O" && c[8]=="O") {
            won=true;
        }
        if (c[2]=="O" && c[4]=="O" && c[6]=="O") {
            won=true;
        }
        //End Checks
        //Tie Checks
        if (checkUsed(0,c)==true && checkUsed(1,c)==true && checkUsed(2,c)==true && checkUsed(3,c)==true && checkUsed(4,c)==true && checkUsed(5,c)==true && checkUsed(6,c)==true && checkUsed(7,c)==true && checkUsed(8,c)==true) {
            won=true;
        }
        return won;
    }
}