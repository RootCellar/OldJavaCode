package com.Darian.RPG;
import java.util.ArrayList;
public class Ability {
    private String name;
    private ArrayList<Parameter> params = new ArrayList<Parameter>();
    public Ability(String n) {
        name=n;
    }
    
    public String toString() {
        String ret="";
        for(int i=0; i<params.size(); i++) {
            ret+=params.get(i).getName()+" "+params.get(i).getValue()+"\n";
        }
        return name+":\n"+ret;
    }
    
    public String getName() {
        return name;
    }
    
    public void addParam(Parameter p) {
        params.add(p);
    }
    
    public ArrayList<Parameter> getParams() {
        return params;
    }
    
    public void remParam(int w) {
        params.remove(w);
    }
    
    public Parameter getParam(String n) {
        for(int i=0; i<params.size(); i++) {
            if(params.get(i).getName().equals(n)) {
                return params.get(i);
            }
        }
        return null;
    }
}