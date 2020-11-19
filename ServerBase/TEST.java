import java.io.*;
public class TEST
{
    public static void main(String[] args) {
        byte[] bytes = readFile("README.TXT");
        //byte[] bytes = readFile("TEST.java");
        
        if(bytes == null) {
            System.out.println("Error on read");
            return;
        }
        
        System.out.println("Reconstructing file as string...");
        
        String fileAsString = "";
        char c = 'a';
        
        for(int i=0; i<bytes.length; i++) {
            c = (char)bytes[i];
            fileAsString += c;
        }
        
        System.out.println("Finished");
        System.out.println(fileAsString);
    }

    public static byte[] readFile(String path) {
        System.out.println("Attempting to read "+path);
        File file = new File(path);
        try{
            if(!file.canRead()) file.createNewFile();
            
            InputStream input = new FileInputStream(file);
            
            System.out.println(file.length());
            
            if(file.length()>Integer.MAX_VALUE) {
                System.out.println("File is too large");
                return null;
            }
            
            byte[] bytes = new byte[(int)file.length()];
            
            System.out.println("Starting read...");
            
            int amountRead = input.read(bytes);
            
            System.out.println("Read finished");
            
            System.out.println(bytes);
            
            for(int i=0; i<amountRead; i++) {
                System.out.println(bytes[i]);
            }
            
            return bytes;
        }catch(Exception e) {
            //System.out.println("Could not read the file");
            return null;
        }
        
        
    }
}