//Darian Marvel
//Calculates Damage
import java.util.Scanner;
public class DamageCalc
{
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("This program calculates damage with given damage and defense stats");
        System.out.println("Make sure to give only integers, this program will crash otherwise");
        int damage;
        int armor;
        while(true) {
            System.out.println("Give me the attack stat: ");
            damage=input.nextInt();
            System.out.println("Give me the defense stat: ");
            armor=input.nextInt();
            System.out.printf("%f damage done\n",CalcDamage(damage,armor));
            
        }
    }

    /**
     * Calculates the damage done
     * @param damage attack stat of the attacker
     * @param armor defense stat of the person being attacked
     * @return The damage done as a double
     */
    public static double CalcDamage(int damage, int armor) {
        double a=damage;
        double b=armor;
        double c=a/b;
        double finalDamage;
        if(a>b) {
            c=c-1;
            c=c/3.0;
            c=c+1;
        }
        if(c<=0.1) {
            return 0;
        }
        else {
            finalDamage=a*c;
            return finalDamage;
        }
    }
}