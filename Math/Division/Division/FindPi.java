public class FindPi
{
    public static void main(String args[]) {
        System.out.println("Ready to calculate pi");
        PiFinder p = new PiFinder();
        p.PLACES = 1000;
        p.LOOPS = 100000000;
        System.out.println("Starting to calculate pi...");
        long start = System.nanoTime();
        int[] pi = p.calcPi();
        long end = System.nanoTime();
        long time = end - start;
        System.out.println("Done finding pi. Printing....");
        System.out.print(pi[0]+".");
        for(int i=1; i<pi.length; i++) {
            System.out.print(pi[i]);
        }
        System.out.println();
        System.out.println("Done");
        System.out.println("Took "+time+" ns");
        double time2 = ( (double) time )/1000000000.0;
        System.out.println("Took "+time2+" s");
    }
}