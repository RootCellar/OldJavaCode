package com.Darian.RPG;
import java.util.Scanner;
/**
 * This thread accepts input from server terminal
 */
public class inThread implements Runnable
{
    public void run() {
        Scanner in= new Scanner(System.in);
        while(!Server.done) {
            Server.setin(in.nextLine());
            try{
                Thread.sleep(100);
            }catch(Exception e) {
                
            }
        }
    }
}