import java.util.Scanner;

public class Encryption {
    public static void main(String args[]) {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Give me a word to encrypt: ");
            String s = input.next();
            String output = "";
            String s2;
            char s3;
            int val;
            int len = s.length();
            for (int i=0;i<len;i++) {
                s3 = s.charAt(i);
                s2 = String.valueOf(s3);
                val = s2.compareTo("a");
                output = output + val + " ";
            }
            System.out.printf("%s\n",output);
        }
    }
}