package com.Darian.RPG;
import java.util.ArrayList;
import java.io.File;
/**
 * This class listens for commands, and sends the info to the command mods
 */
public class ModListener implements Mod
{
    private static ArrayList<Mod> to;
    public ModListener() {
        to = new ArrayList<Mod>();
    }

    public void init() {
        System.out.println("Initializing mods..");
        String[] files = new File("Mods").list();
        for(int i=0; i<files.length; i++) {
            //System.out.println(files[i]);
            if(files[i].indexOf(".class")!=-1) {
                files[i]=files[i].substring(0,files[i].indexOf("."));
            }
            try{
                add((Mod)java.lang.Class.forName("Mods."+files[i]).newInstance());
                System.out.println(files[i]);
            }catch(Exception e) {
                //e.printStackTrace();
            }
        }

        for(int i=0; i<to.size(); i++) {
            to.get(i).init();
        }
        System.out.println("Successfully loaded "+to.size()+" mods.");
    }
    
    public void tick() {
        for(int i=0; i<to.size(); i++) {
            to.get(i).tick();
        }
    }

    public void add(Mod add) {
        to.add(add);
    }

    public void command(String m, readThread from) {
        for(int i=0; i<to.size(); i++) {
            to.get(i).command(m,from);
        }
    }

    public void use(Character user, Character target, Ability ability) {

    }
}