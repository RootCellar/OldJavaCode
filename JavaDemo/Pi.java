public class Pi
{
    public static void main(String args[]) {
        double total=0;
        for(double i=1;i<1000000000;i++) {
            total+=1.0/(i*i*i*i);
            System.out.println(Math.pow(total*90,0.25));
        }
        total=Math.pow((total*90),0.25);
        System.out.println(total);
        System.out.println(Math.PI);
    }
}