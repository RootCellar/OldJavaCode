public class Division
{
    public static void divide(int num1, int num2, int places) {
        //int num1 = 3524578;
        //int num2 = 2178309;
        //int places = 100;
        int x = 0;
        int y = 0;
        
        System.out.println(num1 + " divided by " + num2);
        
        String result ="";
        
        x = num1 / num2;
        y = num1 % num2;
        result += x+".";
        num1=y;
        
        for(int i=0; i<places; i++) {
            
            num1*=10;
            /**
            boolean twice = false;
            while(num1<num2) {

                num1 *= 10;
                
                if(twice) result += "0";
                
                twice = true;
                //result += "";
            }
            */
            
            x = num1 / num2;
            y = num1 % num2;
            
            result += x + "";
            
            num1 = y;
            
        }
        System.out.println(result);
    }
    
    public static int[] divide2(int num1, int num2, int places) {
        //int num1 = 3524578;
        //int num2 = 2178309;
        //int places = 100;
        int x = 0;
        int y = 0;
        
        //System.out.println(num1 + " divided by " + num2);
        
        int[] result = new int[places];
        
        
        x = num1 / num2;
        y = num1 % num2;
        result[0] = x;
        num1=y;
        
        
        for(int i=1; i<places; i++) {
            
            num1*=10;
            
            x = num1 / num2;
            y = num1 % num2;
            
            result[i] = x;
            //result += x + "";
            
            num1 = y;
            
        }
        //System.out.println(result);
        return result;
    }
}