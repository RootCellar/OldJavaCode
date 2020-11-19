import java.util.Scanner;
public class SolveTriangle {
    public static void main(String args[]) {
        Scanner in=new Scanner(System.in);
        double angle1=-1;
        double angle2=-1;
        double side1=-1;
        double side2=-1;
        double side3=-1;
        boolean done=false;
        double in3;
        String in2="";
        while(!done) {
            System.out.println("90");
            System.out.println("          90 degrees");
            System.out.println("            /\\ ");
            System.out.println("     Side 1/  \\ Side 2");
            System.out.println("          /    \\");
            System.out.println("Angle 2  -------- Angle 1");
            System.out.println("         Side 3");
            System.out.println("\n\nAngle 1: "+angle1);
            System.out.println("Angle 2: "+angle2);
            System.out.println("Side 1: "+side1);
            System.out.println("Side 2: "+side2);
            System.out.println("Side 3: "+side3);
            System.out.println("1. Set");
            System.out.println("2. Solve");
            System.out.println("3. Reset");
            
            in2=in.next();
            if(in2.equals("reset")) {
                angle1=-1;
                angle2=-1;
                side1=-1;
                side2=-1;
                side3=-1;
            }
            if(in2.equals("set")) {
                System.out.println("What will you set?: ");
                in2=in.next();
                if(in2.equals("side1")) {
                    System.out.println("What will you set it to?: ");
                    in3=in.nextDouble();
                    side1=in3;
                    System.out.println("Side 1 set");
                }
                if(in2.equals("side2")) {
                    System.out.println("What will you set it to?: ");
                    in3=in.nextDouble();
                    side2=in3;
                    System.out.println("Side 2 set");
                }
                if(in2.equals("side3")) {
                    System.out.println("What will you set it to?: ");
                    in3=in.nextDouble();
                    side3=in3;
                    System.out.println("Side 3 set");
                }
                if(in2.equals("angle1")) {
                    System.out.println("What will you set it to?: ");
                    in3=in.nextDouble();
                    angle1=in3;
                    System.out.println("Angle 1 set");
                }
                if(in2.equals("angle2")) {
                    System.out.println("What will you set it to?: ");
                    in3=in.nextDouble();
                    angle2=in3;
                    System.out.println("Angle 2 set");
                }
            }
            if(in2.equals("solve")) {
                /**
                 * Use a lot of if statements and Math.cos(), Math.sin(), and Math.tan()
                 * along with their inverses
                 * Starts by finding angles where possible, then side lengths
                 */
                if(angle1==-1) {
                    if(side1!=-1 && side2!=-1) {
                        angle1=Math.toDegrees(Math.atan(side1/side2));
                    }
                    if(side1!=-1 && side3!=-1) {
                        angle1=Math.toDegrees(Math.asin(side1/side3));
                    }
                    if(side2!=-1 && side3!=-1) {
                        angle1=Math.toDegrees(Math.acos(side2/side3));
                    }
                }
                if(angle2==-1) {
                    if(side1!=-1 && side2!=-1) {
                        angle2=Math.toDegrees(Math.atan(side2/side1));
                    }
                    if(side2!=-1 && side3!=-1) {
                        angle2=Math.toDegrees(Math.asin(side2/side3));
                    }
                    if(side1!=-1 && side3!=-1) {
                        angle2=Math.toDegrees(Math.acos(side1/side3));
                    }
                }
                
                if(side1==-1) {
                    if(angle1!=-1 && side2!=-1) {
                        side1=side2*Math.tan(Math.toRadians(angle1));
                    }
                    if(angle1!=-1 && side3!=-1) {
                        side1=side3*Math.sin(Math.toRadians(angle1));
                    }
                    if(angle2!=-1 && side3!=-1) {
                        side1=side3*Math.cos(Math.toRadians(angle2));
                    }
                }
                if(side2==-1) {
                    if(angle1!=-1 && side1!=-1) {
                        side2=side1/Math.tan(Math.toRadians(angle1));
                    }
                    if(angle2!=-1 && side3!=-1) {
                        side2=side3*Math.sin(Math.toRadians(angle2));
                    }
                    if(angle1!=-1 && side3!=-1) {
                        side2=side3*Math.cos(Math.toRadians(angle1));
                    }
                    if(angle2!=-1 && side1!=-1) {
                        side2=side1*Math.tan(Math.toRadians(angle2));
                    }
                }
                if(side3==-1) {
                    if(angle1!=-1 && side1!=-1) {
                        side3=side1/Math.sin(Math.toRadians(angle1));
                    }
                    if(angle2!=-1 && side1!=-1) {
                        side3=side1/Math.cos(Math.toRadians(angle2));
                    }
                    if(angle1!=-1 && side2!=-1) {
                        side3=side2/Math.cos(Math.toRadians(angle1));
                    }
                    if(angle2!=-1 && side2!=-1) {
                        side3=side2/Math.sin(Math.toRadians(angle2));
                    }
                }
            }
        }
        
    }
}