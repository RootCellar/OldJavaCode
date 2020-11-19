public class checker implements Runnable
{
    boolean isDone=true;
    int num=-1;
    boolean isPrime=false;
    boolean isGoing=true;
    public void run() {
        while(isGoing) {
            if(!isDone && num!=-1) {
                isPrime=true;
                for(int i=2; i<=Math.sqrt(num); i++) {
                    if(num%i==0) {
                        isPrime=false;
                        break;
                    }
                }
                isDone=true;
            }
        }
    }
}