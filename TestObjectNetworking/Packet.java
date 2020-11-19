import java.io.*;
public class Packet implements Serializable
{
    String name;
    String value;
    int code;
    
    public Packet(String n, String v) {
        name=n;
        value=v;
        code=hashCode();
    }
    
    public String toString() {
        return "NAME: "+name+"\nVALUE: "+value+"\nCODE: "+code;
    }
}