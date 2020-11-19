import java.util.ArrayList;
import java.io.*;

public class prime2 {
    public synchronized static void main(String args[]) {
        ArrayList<checker> checks = new ArrayList<checker>();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        ArrayList<Integer> primes = new ArrayList<Integer>();
        checker j;
        Thread j2;

        int maxCheck = 10000000;

        for(int i=0; i<3; i++) {
            j=new checker();
            j2=new Thread(j);
            checks.add(j);
            threads.add(j2);
            j2.start();
        }
        boolean next=false;
        long time1=System.nanoTime();

        long checkTime = System.nanoTime();
        for(int i=2; i<maxCheck; i++) {
            next=false;
            while(!next) {
                for(int x=0; x<checks.size(); x++) {
                    if(checks.get(x).isDone) {
                        if(checks.get(x).isPrime) {
                            primes.add(new Integer(checks.get(x).num));
                        }
                        checks.get(x).num=i;
                        checks.get(x).isDone=false;
                        next=true;
                        x=checks.size();
                    }
                }
            }

            if(System.nanoTime() - checkTime > 1000000000) {
                int donePerc = (int) ( ( (double)i / (double) maxCheck ) * 100.0 );
                System.out.println(donePerc + "% (" + ( ( System.nanoTime() - time1 ) / 1000000000 ) + "s)");
                checkTime = System.nanoTime();
            }

        }

        for(int i=0; i<checks.size(); i++) {
            if(!checks.get(i).isDone) {
                while(!checks.get(i).isDone) {}
                if(checks.get(i).isPrime) primes.add( new Integer( checks.get(i).num ) );
            }
        }

        long time2=System.nanoTime();
        long time3=time2-time1;
        System.out.println(time3+" nanoseconds");
        System.out.println("About "+time3/1000000000+" seconds");
        for(int i=0; i<threads.size(); i++) {
            checks.get(i).isGoing=false;
        }
        System.out.println("Found "+primes.size()+" prime numbers");

        FileWriter writer;
        System.out.println("Creating file writer...");
        try{
            writer = new FileWriter("primes.txt");
        }catch(Exception e) {
            System.out.println("Couldn't open writer");
            return;
        }
        
        System.out.println("Writing...");
        
        int counter = 0;

        for( Integer i : primes ) {
            counter++;
            try{
                writer.write(counter + ": " + i.toString() + System.getProperty("line.separator") );
            }catch(Exception e) {
                System.out.println("Error while writing");
                return;
            }
        }
        System.out.println("Done");
    }
}