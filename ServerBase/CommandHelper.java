public class CommandHelper
{
    /**
     * Used to ensure that names of users and files have the correct length,
     * and that they only use letters and numbers
     */
    public static boolean isValid(String s, int min, int max) {
        boolean isValid;
        char validChar;
        
        if(s.length()<min) {
            return false;
        }
        if(s.length()>max) {
            return false;
        }
        
        for(int i=0; i<s.length(); i++) {
            isValid=false;
            validChar='a';
            for(int w=0; w<26; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }

            validChar='A';
            for(int w=0; w<26; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }

            validChar='0';
            for(int w=0; w<10; w++) {
                if(s.substring(i,i+1).equals(String.valueOf(validChar))) {
                    isValid=true;
                }
                validChar++;
            }
            
            if(isValid==false) {
                return false;
            }
        }
        
        return true;
    }
}