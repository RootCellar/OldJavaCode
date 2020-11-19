public class PiFinder
{
    int PLACES = 10000;
    int LOOPS = 100;
    
    public int[] calcPi() {
        int[] pi = new int[PLACES];
        
        for(int i=0; i<PLACES; i++) {
            pi[i]=0;
        }
        
        //pi = add( Division.divide2(1,7,PLACES), Division.divide2(1,-9,PLACES) );
        
        for(int i=0; i<LOOPS; i++) {
            int num1 = (int)Math.pow(-1,i);
            int num2 = (i*2) + 1;
            pi = add( pi, Division.divide2(num1,num2,PLACES) );
        }
        pi = mult(pi,4);
        return pi;
    }
    
    public int[] mult(int[] one, int two) {
        int[] res = new int[one.length];
        for(int i=0; i<one.length; i++) {
            res[i]=0;
            res[i]=one[i]*two;
        }
        
        for(int i=res.length-1; i>0; i--) {
            while(res[i]>9) {
                res[i]-=10;
                res[i-1]+=1;
            }
        }
        
        return res;
    }
    
    public int[] add(int[] one, int[] two) {
        int[] res = new int[one.length];
        for(int i=0; i<one.length; i++) {
            res[i]=0;
            res[i]=one[i]+two[i];
        }
        
        for(int i=res.length-1; i>0; i--) {
            while(res[i]>9) {
                res[i]-=10;
                res[i-1]+=1;
            }
        }
        
        for(int i=res.length-1; i>0; i--) {
            while(res[i]<0) {
                res[i]+=10;
                res[i-1]-=1;
            }
        }
        
        return res;
    }
}