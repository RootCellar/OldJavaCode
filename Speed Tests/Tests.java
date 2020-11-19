public class Tests {
    public static void main(String args[]) {
        long time1=System.nanoTime();
        int ticks=0;
        while(true) {
            ticks++;
            try{
                Thread.sleep(0);
            }catch(Exception e) {
                
            }
            if(System.nanoTime()-time1>=1000000000) {
                System.out.println(ticks);
                ticks=0;
                time1=System.nanoTime();
                //break;
            }
        }
    }
}