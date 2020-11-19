package com.Darian.RPG;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
/**
 * Use "Server.methodname()" to access these methods
 * 
 */
public class Server implements Runnable
{
    static ArrayList<readThread> r=new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static ArrayList<Character> characters = new ArrayList<Character>();
    static ArrayList<Race> races = new ArrayList<Race>();
    static ArrayList<Class> classes = new ArrayList<Class>();
    static ArrayList<Ability> abilities = new ArrayList<Ability>();
    static String in="";
    static ModListener mod;
    static int mapSize=1000;
    static boolean isServer=true;
    static int port=0;
    static boolean isHalted=false;
    static GUI gui = new GUI();
    static TextBox gui2 = new TextBox();
    static int ticks2=0;
    static int AILimit=1000;
    static boolean done=false;
    static double mult = 1.01;
    static File log;
    static FileWriter toLog;
    public static void main(String args[]) {
        Server s = new Server();
        Thread t = new Thread(s);
        t.start();
    }

    public static int getPort() {
        return port;
    }

    public static void notServer() {
        isServer=false;
    }

    /**
     * This method runs the server
     */
    public void run() {
        System.out.println("Starting Server...");
        System.out.println("Starting log writer...");
        Date date = new Date();
        try{
            log = new File("Logs/"+now(date)+".txt");
            log.createNewFile();
            toLog = new FileWriter(log,true);
        }catch(Exception e) {
            //e.printStackTrace();
            System.out.println("Failed to create log");
        }
        log("Log file created and opened");
        
        System.out.println("Starting mod loader...");
        log("Loading mods...");
        
        mod = new ModListener();
        mod.init();
        
        log("Finished");
        System.out.println("Mod loader finished");

        new File("Races").mkdir();
        new File("Classes").mkdir();
        new File("Players").mkdir(); //Makes sure that folders are set up
        new File("Mail").mkdir();
        new File("Logs").mkdir();
        new File("Abilities").mkdir();

        System.out.println("Loading abilities..");
        log("Loading abilities..");
        makeAbilities();
        System.out.println("Loading races...");
        log("Loading races...");
        makeRaces();
        System.out.println("Loading classes...");
        log("Loading classes...");
        makeClasses();

        checker w = new checker();
        Thread w2 = new Thread(w); //This thread reloads guns and keeps players in map bounds
        w2.start();
        w.setMapSize(mapSize);

        ServerSocket server=null; //Socket used to accept connections

        Thread c2=null;
        if(isServer) {
            inThread c = new inThread();
            c2 = new Thread(c); //Allows input in server terminal
            c2.start();
        }

        readThread t;
        Thread t2; //Used when a player connects

        int x=-1;
        int y=-1;
        port=0;
        System.out.println("Finding open port...");
        log("Finding open port...");
        for(int i=1; i<65535; i++) {
            try{
                server = new ServerSocket(i);
                server.setSoTimeout(1);
                port=i;
                break;
            }catch(Exception e) {

            }
        }
        if(server==null) {
            System.out.println("Failed to find a valid port");
            log("Could not find a valid port");
            return;
        }
        Socket client=null;
        for(int i=0; i<AILimit; i++) {
            genChar(true);
        }
        System.out.println("Server Started.");
        System.out.println("Use /help to get command list.");
        System.out.println("Port: "+port);
        log("Server started with port "+port);
        long time1=System.nanoTime();
        int ticks=0;
        long start=System.nanoTime();
        gui2.write("Server started");
        while(!done) { //Main Server loop
            start=System.nanoTime();
            //System.gc();
            gui.update();
            genChar(true);
            try{
                try{
                    //Remove players that are no longer connected
                    for(int i=0; i<r.size(); i++) {
                        if(r.get(i).isDone()==true) {
                            deleteThread(i);
                            //System.out.println("DEBUG: Removed "+i);
                        }
                        if(r.get(i).isGoing()==false) {
                            r2.get(i).interrupt();
                        }
                    }
                    client = null;
                    client = server.accept(); //Tries to accept a client connection

                    if(client!=null) { //If client connects, set them up
                        System.out.println("Connected to "+client.getRemoteSocketAddress());
                        System.out.println("Setting up threads...");
                        send(client.getRemoteSocketAddress()+" connected.");
                        log("Connected to "+client.getRemoteSocketAddress());
                        t=new readThread();
                        t2=new Thread(t);
                        t.setSocket(client);
                        if(r.size()<50) {
                            log("Adding "+client.getRemoteSocketAddress() +" to player list...");
                            r.add(t);
                            r2.add(t2);
                            t.setNumber(r.size()-1);
                            t2.start();
                        }
                        else { //If there are too many players, close connection
                            sendTo("Sorry, too many players!",client);
                            sendTo("You will now be kicked.",client);
                            send(client.getRemoteSocketAddress()+" disconnected (too many players)");
                            client.close();
                            t.stopGoing();
                        }
                    }
                }catch(SocketTimeoutException s) {

                }catch(IOException e) {
                    //e.printStackTrace();
                }
                //Commands
                if(isServer) {
                    if(in.equals("/races")) {
                        for(int i=0; i<races.size(); i++) {
                            System.out.println(i+". "+races.get(i).getName()+" "+races.get(i).getRegenMult()+" "+races.get(i).getHpMult()+" "+races.get(i).getAtkMult()+" "+races.get(i).getDefMult()+" "+races.get(i).getMAtkMult()+" "+races.get(i).getManaMult());
                        }
                        in="";
                    }
                    else if(in.equals("/classes")) {
                        for(int i=0; i<classes.size(); i++) {
                            System.out.println(i+". "+classes.get(i).getName()+" "+classes.get(i).getBHp()+" "+classes.get(i).getBMana()+" "+classes.get(i).getBAtk()+" "+classes.get(i).getBDef()+" "+classes.get(i).getBMatk());
                        }
                        in="";
                    }
                    else if(in.equals("/abilities")) {
                        for(int i=0; i<classes.size(); i++) {
                            System.out.println(i+". "+abilities.get(i));
                        }
                        in="";
                    }
                    else if(in.equals("/stop")) {
                        done=true;
                        send("Stopping server...");
                    }
                    else if(upToSpace(in,false).equals("/mail ")&& in.length()>6 && in.indexOf("END")==-1) {
                        mail(in.substring(in.indexOf(" ")+1)+" from Server");
                        in="";
                    }
                    else if(in.equals("/getmail")) {
                        System.out.println(getMail("Server"));
                        in="";
                    }
                    else if(in.equals("/delmail")) {
                        delMail("Server");
                        System.out.println("Mail deleted");
                        in="";
                    }
                    else if(in.equals("/help")) {
                        System.out.println("/Stop - Stop the server");
                        System.out.println("/help - view command list");
                        System.out.println("/list - view player list. \nIt shows number, ip, name(if set), Tank, and some debug info");
                        System.out.println("/kick <number> - use /list to \nfind the number of the player, than use /kick (number) to disconnect them");
                        System.out.println("/kickall - kicks every player \noff of the server");
                        System.out.println("/map <number> - sets the map size to number. The map starts out 200x200 (Map size 100)");
                        System.out.println("/mail <name> <message> - mails message to name");
                        System.out.println("/getmail - reads mail");
                        System.out.println("/delmail - deletes mail");
                        System.out.println("/ticks - View number of ticks over the last second");
                        System.out.println("/races - lists races");
                        System.out.println("/classes - lists classes");
                        System.out.println("/abilities - lists abilities");
                        in="";
                    }
                    else if(in.equals("/list")) { //Shows player list
                        in="";
                        for(int i=0; i<r.size(); i++) {
                            System.out.println(i+". "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName()+" Level "+r.get(i).getUser().getLevel()+" "+r.get(i).getUser().getRace().getName()+" "+r.get(i).getUser().getType().getName());
                        }
                    }
                    else if(in.length()>=6 && in.substring(0,6).equals("/kick ")) {
                        try{
                            kick(Integer.parseInt(in.substring(6)));
                        }catch(NumberFormatException e) {
                            System.out.println("Please type a number");
                        }
                        in="";
                    }
                    else if(in.length()>=7 && in.substring(0,7).equals("/limit ")) {
                        try{
                            AILimit=Integer.parseInt(in.substring(7));
                        }catch(NumberFormatException e) {
                            System.out.println("Please type a number");
                        }
                        in="";
                    }
                    else if(in.length()>=5 && in.substring(0,5).equals("/map ")) { //Changes map size
                        try{
                            w.setMapSize((Integer.parseInt(in.substring(5))));
                            mapSize=(Integer.parseInt(in.substring(5)));
                            System.out.println("Map size changed");
                            send("Map size changed to "+Integer.parseInt(in.substring(5)));
                        }catch(NumberFormatException e) {
                            System.out.println("Please type a number");
                        }
                        in="";
                    }
                    else if(in.equals("/kickall")) {
                        send("Kicking everyone off of the server.");
                        for(int i=r.size()-1; i>-1; i--) {
                            kick(i);
                        }
                        in="";
                    }
                    else if(in.equals("/ticks")) {
                        System.out.println(ticks2);
                        in="";
                    }
                    else if(in.equals("/halt")) {
                        halt(!isHalted);
                        in="";
                    }
                    else if(in.length()>1 && in.substring(0,1).equals("/")) {
                        System.out.println("Invalid command. Use /help to get command list");
                        in="";
                    }
                    else if(!in.equals("")) {
                        send(in);
                        in="";
                    }
                }

            }catch(Exception e) { //Catches any errors
                //send("Server error");
                //e.printStackTrace();
                in="";
            }
            try{
                ticks++;
                if(System.nanoTime()-time1>=1000000000) {
                    //System.out.println(ticks);
                    time1=System.nanoTime();
                    ticks2=ticks;
                    ticks=0;
                }
            }catch(Exception e) {

            }
            if(!isHalted) {
                doAi();
            }
            if(isHalted) {
                stableWait(1000,start);
            }
            stableWait(100,start);
        }
        log("Server shutting down...");
        isHalted=false;
        w2.stop(); //Closes down threads
        if(isServer) {
            c2.stop();
        }
        for(int i=0; i<r.size(); i++) {
            r.get(i).stopGoing();
            try{
                r.get(i).getSocket().close();
            }catch(Exception e) {

            }
            while(!r.get(i).isDone) {
                r2.get(i).interrupt();
                try{
                    Thread.sleep(1);
                }catch(Exception e) {

                }
            }
        }
        log("Server shut down successfully");
        try{
            server.close(); //Closes server socket
            toLog.flush(); // Makes sure that everything is written
        }catch(Exception e) {

        }
        try{
            toLog.close(); //close log writer
        }catch(Exception e) {
        }
        System.out.println("Finished.");
        return;
    }

    public static ArrayList<String> getArgs(String s) {
        ArrayList<String> args = new ArrayList<String>();
        while(s.length()>0) {
            args.add( upToSpace(s,true) );
            subUpToSpace(s);
        }
        return args;
    }

    /**
     * Calculates the damage done
     * @param damage attack stat of the attacker
     * @param armor defense stat of the person being attacked
     * @return The damage done as a double
     */
    public static double calcDamage(double damage, double armor) {
        double a=damage;
        double b=armor;
        double c=a/b;
        double finalDamage;
        if(a>b) {
            c=c-1;
            c=c/3.0;
            c=c+1;
        }
        if(c<=0.1) {
            return 0;
        }
        else {
            finalDamage=a*c;
            return finalDamage;
        }
    }

    public static boolean isPlayer(Character c) {
        try{
            for(int i=0; i<r.size(); i++) {
                if(r.get(i).getUser().equals(c)) {
                    return true;
                }
            }
        }catch(Exception e) {

        }
        return false;
    }

    public static readThread getCharHandler(Character ch) {
        if(isPlayer(ch)) {
            for(int i=0; i<r.size(); i++) {
                if(r.get(i).getUser().equals(ch)) {
                    return r.get(i);
                }
            }
        }
        return null;
    }

    public static boolean use(Ability use, Character who, Character target) {
        //gui2.write("Calling use function for "+use.getName());
        Socket send=null;
        Socket send2=null;
        try{
            send=getCharHandler(who).getSocket();
        }catch(Exception e) {

        }

        try{
            send2=getCharHandler(target).getSocket();
        }catch(Exception e) {

        }
        for(int i=0; i<use.getParams().size(); i++) {
            Parameter param = use.getParams().get(i);
            if(param.getName().equals("TMDAMAGE")) {
                int bdamage;
                try{
                    bdamage=Integer.parseInt(param.getValue());
                }catch(Exception e) {
                    gui2.write(use.getName()+ " TMDAMAGE flag incorrectly defined");
                    sendTo(use.getName()+ " TMDAMAGE flag incorrectly defined",send);
                    return false;
                }
                double damage=(((double)bdamage)/100)*who.getMAtk();
                target.damage(calcDamage(damage,target.getDef()));
                sendTo("You did "+calcDamage(damage,target.getDef())+ " damage.",send);
                sendTo("You took "+calcDamage(damage,target.getDef())+ " damage from "+who.getName(),send2);
            }

            if(param.getName().equals("TDAMAGE")) {
                int bdamage;
                try{
                    bdamage=Integer.parseInt(param.getValue());
                }catch(Exception e) {
                    gui2.write(use.getName()+ " TDAMAGE flag incorrectly defined");
                    sendTo(use.getName()+ " TDAMAGE flag incorrectly defined",send);
                    return false;
                }
                double damage=(((double)bdamage)/100)*who.getAtk();
                target.damage(calcDamage(damage,target.getDef()));
                sendTo("You did "+calcDamage(damage,target.getDef())+ " damage.",send);
                sendTo("You took "+calcDamage(damage,target.getDef())+ " damage from "+who.getName(),send2);
            }

            if(param.getName().equals("HEAL")) {
                int bheal;
                try{
                    bheal=Integer.parseInt(param.getValue());
                }catch(Exception e) {
                    gui2.write(use.getName()+ " HEAL flag incorrectly defined");
                    sendTo(use.getName()+ " HEAL flag incorrectly defined",send);
                    return false;
                }
                double heal=(((double)bheal)/100)*who.getMAtk();
                target.heal(heal);
                sendTo("You healed your target for "+heal+ " health.",send);
                sendTo(who.getName()+" healed you for "+heal+ " health.",send2);
            }
        }

        return true;
    }

    public static void doAi() {
        for(int i=0; i<characters.size(); i++) {
            try{
                if(!isPlayer(characters.get(i)) && characters.get(i).isAlive()) {
                    for(int j=0; j<characters.size(); j++) {
                        if(sameSpot(characters.get(i),characters.get(j)) && i!=j) {
                            use(characters.get(i).getType().getAbilities().get(0),characters.get(i),characters.get(j));
                        }
                    }
                }
            }catch(Exception e) {
                //e.printStackTrace();
            }
        }
    }

    public static void stableWait(int time,long start) {
        long time2=System.nanoTime();
        while( time2 - start < time*1000000 ) {
            try{
                Thread.sleep(1);
            }catch(Exception e) {

            }
            time2=System.nanoTime();
        }
    }

    /**
     * Gets the distance between two character
     */
    public static double getDistance(Character one, Character two) {
        int x1=one.getX();
        int y1=one.getY();
        int x2=two.getX();
        int y2=two.getY();
        return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
    }

    public static boolean sameSpot(Character one, Character two) {
        if(getDistance(one,two)==0) {
            return true;
        }   
        return false;
    }

    public static String look(Character sp) {
        String ret="";
        gui2.write(sp.getName() + " is looking around");
        for(int i=0; i<characters.size(); i++) {
            if(!sp.equals(characters.get(i)) && sameSpot(sp, characters.get(i))) {
                ret+=characters.get(i).getName() +" the Level "+characters.get(i).getLevel() +" "+characters.get(i).getRace().getName()+" "+characters.get(i).getType().getName()+"\n";
            }
        }
        return ret;
    }

    public static String spot(Character sp) {
        gui2.write("Running spotting system for "+sp.getName());
        String ret="Spotted:\n";
        for(int i=0; i<characters.size(); i++) {
            if(!sp.equals(characters.get(i))) {
                ret+=characters.get(i).getName()+" "+getDistance(sp,characters.get(i))+" "+characters.get(i).getLevel()+"\n";
            }
        }
        return ret;
    }

    public static void genChar(boolean x) {
        if((Math.random()<0.1 || x) && characters.size()<AILimit && !isHalted) {
            Character newChar = new Character( "a bot", classes.get(0), races.get(0), mult );
            //characters.add(newChar);
            newChar.setXp((int)(Math.random()*1000000.0));
            newChar.setX((int)(Math.random()*mapSize));
            newChar.setY((int)(Math.random()*mapSize));
            //newChar.setX(0);
            //newChar.setY(0);
            newChar.revive();
            newChar.calcStats();
            characters.add(newChar);
        }
    }

    public static void halt(boolean b) {
        isHalted=b;
        if(b) {
            System.out.println("Server activity halted");
            send("Activity halted");
        }
        else {
            System.out.println("Server activity not halted");
            send("Activty no longer halted");
        }
    }

    public static boolean isHalted() {
        return isHalted;
    }

    public static ArrayList<Race> getRaces() {
        return races;
    }

    public static ArrayList<Class> getClasses() {
        return classes;
    }

    public static void makeAbilities() {
        for(int i=0; i<10000; i++) {
            if(new File("Abilities/"+Integer.toString(i)+".txt").canRead()==true) {
                readAbility(i);
            }
        }
    }

    public static void readAbility(int i) {
        try{
            Scanner in = new Scanner(new File("Abilities/"+Integer.toString(i)+".txt"));
            Ability newA=new Ability(in.next());
            while(in.hasNext()) {
                newA.addParam(new Parameter( in.next() , in.next() ));
            }
            abilities.add(newA);
            gui2.write(newA.toString());
        }catch(Exception e) {

        }
    }

    /**
     * Looks for race files and has another method read them
     */
    public static void makeRaces() {
        for(int i=0; i<100; i++) {
            if(new File("Races/"+Integer.toString(i)+".txt").canRead()==true) {
                readRace(i);
            }
        }
    }

    /**
     * Used to read a race file and add it to race list
     */
    public static void readRace(int i) {
        try{
            Scanner in = new Scanner(new File("Races/"+Integer.toString(i)+".txt"));
            races.add(new Race(in.next(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextDouble()));
            gui2.write(races.get(races.size()-1).toString());
        }catch(Exception e) {

        }
    }

    /**
     * Looks for class files and has another method read them
     */
    public static void makeClasses() {
        for(int i=0; i<100; i++) {
            if(new File("Classes/"+Integer.toString(i)+".txt").canRead()==true) {
                readClass(i);
            }
        }
    }

    /**
     * Used to read a class file and add it to class list
     */
    public static void readClass(int i) {
        try{
            Scanner in = new Scanner(new File("Classes/"+Integer.toString(i)+".txt"));
            String n;
            Class c=new Class(in.next(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextDouble(),in.nextBoolean());
            classes.add(c);
            while(in.hasNext()) {
                n=in.next();
                for(int x=0; x<abilities.size(); x++) {
                    if(abilities.get(x).getName().equals(n)) {
                        c.addAbility(abilities.get(x));
                    }
                }
            }
            gui2.write(c.toString());
        }catch(Exception e) {

        }
    }

    public static void print(String s) {
        if(isServer) {
            System.out.println(s);
        }
    }

    /**
     * Returns all of the threads, which contain info for the logged in players
     */
    public static ArrayList<readThread> getThreads() {
        return r;
    }

    /**
     * Used by readThread to get the command listener
     */
    public static ModListener getListener() {
        return mod;
    }

    /**
     * Returns the remainder of the string given after the first space
     */
    public static String subUpToSpace(String s) {
        String s2="";
        int x=0;
        if(s==null) {
            return "";
        }
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return "";
            }
        }

        return s.substring(x,s.length()); 
    }

    /**
     * If b is true, this method returns s up to the first space.
     * If b is false, this method returns s up to and including the first space
     */
    public static String upToSpace(String s, boolean b) {
        String s2="";
        int x=0;
        if(s==null) {
            return "";
        }
        if(s.length()==0) {
            return s;
        }
        while(!s2.equals(" ")) {
            s2=s.substring(x,x+1);
            x++;
            if(x>s.length()-1) {
                return s;
            }
        }
        if(b==true) {
            return s.substring(0,x-1);
        }
        return s.substring(0,x);
    }

    /**
     * Kicks a player based on their number
     */
    public static void kick(int i) {
        try{
            r.get(i).getSocket().close();
            r.get(i).stopGoing();
        }catch(Exception e) {

        }
        System.out.println("Kicked ("+i+") "+r.get(i).getName());
    }

    /**
     * Used to remove variables of disconnected user
     */
    public static void deleteThread(int i) {
        removeChar(r.get(i).getUser());
        r.remove(i);
        r2.remove(i);
        for(int w=0; w<r.size();w++) {
            r.get(w).setNumber(w);
        }
    }

    public static void removeChar(Character r) {
        for(int i=0; i<characters.size(); i++) {
            if(r.equals(characters.get(i))) {
                characters.remove(i);
                i=characters.size();
            }
        }
    }

    /**
     * Sends a message to every connected client in the format "Server: <message>"
     */
    public static void send(String message) {
        Socket client;
        print("Server: "+message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF("Server: "+message);
            }catch(IOException e) {

            }
        }
    }

    /**
     * Sends a message to every connected client in the format "<message>"
     */
    public static void sendAll(String message) {
        Socket client;
        print(message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF(message);
            }catch(IOException e) {

            }
        }
    }

    /**
     * Sends message to socket "to" in the format "Server: <message>"
     */
    public static void send(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF("Server: "+message);
        }catch(IOException e) {

        }
    }

    /**
     * Used by readThread to send a message to another player
     */
    public static void sendTo(String message, int who) {
        try{
            new DataOutputStream(r.get(who).getSocket().getOutputStream()).writeUTF(message);
        }catch(IOException e) {

        }
    }

    /**
     * Sends message to socket "to" in the format "<message>"
     */
    public static void sendTo(String message, Socket to) {
        try{
            new DataOutputStream(to.getOutputStream()).writeUTF(message);
        }catch(Exception e) {

        }
    }

    /**
     * Used by readThread to send chat messages
     */
    public static void send(Socket from, String message, String m2) {
        Socket client;
        print(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
            }catch(IOException e) {

            }
        }
    }

    /**
     * Deletes mail file of a given player name
     */
    public static void delMail(String name) {
        new File("Mail/"+name+".txt").delete();
    }

    /**
     * Gets the players mail (username given)
     */
    public static String getMail(String name) {
        if(new File("Mail/"+name+".txt").canRead()==false) {
            return "No mail!";
        }
        try{
            Scanner in = new Scanner(new File("Mail/"+name+".txt"));
            String ret="";
            String use="";
            while(in.hasNext()) {
                //ret+=in.next();
                use="";
                while(!use.equals("END") && in.hasNext()) {
                    use=in.next();
                    if(!use.equals("END")) {
                        ret+=use+" ";
                    }
                }
                //System.out.println(ret);
                ret+="\n";
            }
            in.close();
            return ret;
        }catch(Exception e) {
            e.printStackTrace();
            return "Exception when reading mail";
        }
    }

    /**
     * Sends mail to another player
     */
    public static String mail(String message) {
        String to = upToSpace(message,true);
        if(new File("Players/"+to+".txt").canRead()==false && !to.equals("Server")) { //If the receiving player does not exist
            return "Player not found!";
        }
        try{ //Writes mail to file
            FileWriter outf=new FileWriter(new File("Mail/"+to+".txt"),true);
            outf.write(message.substring(to.length()+1)+" END ");
            outf.flush();
            outf.close();
            System.out.println("Message: "+message);
            return "Mail sent!";
        }catch(Exception e) {
            e.printStackTrace();
            return "Error when sending mail (Try again)";
        }
        //return to;
    }

    public static void save(readThread user, String name, String pass) {
        try{
            FileWriter outf = new FileWriter(new File("Players/"+name+".txt"));
            outf.write(pass+" ");
            outf.write("Class: "+user.getUser().getType().getName()+" ");
            outf.write("Race: "+user.getUser().getRace().getName()+ " ");
            outf.write("XP: "+user.getUser().getXP()+" ");
            outf.write("Gold: "+user.getGold());
            outf.flush();
            outf.close();
        }catch(Exception e) {
            System.out.println("Could not save "+name);
            return;
        }
    }

    public static void savef(readThread user, String name, String pass) {
        try{
            FileWriter outf = new FileWriter(new File("Players/"+name+".txt"));
            outf.write(pass+" ");
            outf.flush();
            outf.close();
        }catch(Exception e) {
            System.out.println("Could not save "+name);
            return;
        }
    }

    public static void read(readThread user, Character user2) {
        try{
            Scanner inf = new Scanner(new File("Players/"+user.getName()+".txt"));
            inf.next();
            String in="";
            Class forUser=null;
            Race forUser2=null;
            int xp=0;
            while(inf.hasNext()) {
                in=inf.next();
                if(in.equals("Class:")) {
                    in=inf.next();
                    for(int i=0; i<classes.size(); i++) {
                        if(classes.get(i).getName().equals(in)) {
                            forUser=classes.get(i);
                        }
                    }
                }
                if(in.equals("Race:")) {
                    in=inf.next();
                    for(int i=0; i<races.size(); i++) {
                        if(races.get(i).getName().equals(in)) {
                            forUser2=races.get(i);
                        }
                    }
                }
                if(in.equals("XP:")) {
                    try{
                        xp=Integer.parseInt(inf.next());
                    }catch(Exception e) {

                    }
                }
                if(in.equals("Gold:")) {
                    user.setGold(Integer.parseInt(inf.next()));
                }
            }
            user.setUser(new Character(user.getName(), forUser, forUser2, mult));
            user.getUser().setXp(xp);
        }catch(Exception e) {
            System.out.println("Could not read "+user.getName());
            e.printStackTrace();
            return;
        }
    }

    /**
     * Registers a player
     * @param u Username
     * @param p Password
     */
    public static String authenr(String u, String p, readThread rt) {
        try{
            String use="";
            boolean isValid;
            char validChar;
            //Makes sure that username and password have decent lengths,
            //And that they have letters and numbers ONLY
            if(u.length()<3) {
                return "Username too short!";
            }
            if(u.length()>10) {
                return "Username too long!";
            }
            if(p.length()<3) {
                return "Password too short!";
            }
            if(p.length()>10) {
                return "Password too long!";
            }
            for(int i=0; i<u.length(); i++) {
                isValid=false;
                validChar='a';
                for(int w=0; w<26; w++) {
                    if(u.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }

                validChar='A';
                for(int w=0; w<26; w++) {
                    if(u.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }

                validChar='0';
                for(int w=0; w<10; w++) {
                    if(u.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }
                if(isValid==false) {
                    return "Invalid Username. Please use Letters and numbers only";
                }
            }

            for(int i=0; i<p.length(); i++) {
                isValid=false;
                validChar='a';
                for(int w=0; w<26; w++) {
                    if(p.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }

                validChar='A';
                for(int w=0; w<26; w++) {
                    if(p.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }

                validChar='0';
                for(int w=0; w<10; w++) {
                    if(p.substring(i,i+1).equals(String.valueOf(validChar))) {
                        isValid=true;
                    }
                    validChar++;
                }
                if(isValid==false) {
                    return "Invalid Password. Please use Letters and numbers only";
                }
            }

            if(new File("Players/"+u+".txt").canRead()==true) { //If username is already used
                return "Username already taken";
            }
            if(new File("Players/"+u+".txt").canRead()==false) { //Creates new player file
                //FileWriter outf = new FileWriter(new File("Players/"+u+".txt"));
                savef(rt,u,p);
                //outf.flush();
                //outf.close();
                System.out.println("Registered "+u+" with password "+p);
                return "Register";
            }
        }catch(Exception e) {
            e.printStackTrace();
            return "Error when finding file";
        }
        return "";
    }

    /**
     * Logs a player in
     * @param u Username
     * @param p Password
     * @param to Used to give player data to thread
     */
    public static String authen(String u, String p, readThread to) {
        try{
            String use="";
            for(int i=0; i<r.size(); i++) { //Checks to see if the account is already logged into
                if(r.get(i).getName().equals(u)) {
                    return "That player is already logged in!";
                }
            }
            if(new File("Players/"+u+".txt").canRead()==true) {
                Scanner in = new Scanner(new File("Players/"+u+".txt"));
                if(in.next().equals(p)) {
                    in.close();
                    return "Logged in";
                }
                else { //If player used wrong password
                    System.out.println(u+" used the wrong password ( "+p+" )");
                    return "Wrong password";
                }
            }
            if(new File("Players/"+u+".txt").canRead()==false) { //If username is not set
                return "That username is not set";
            }
        }catch(Exception e) {
            e.printStackTrace();
            return "Error when finding file";
        }
        return "";
    }

    /**
     * Used by inThread to receive input from server terminal
     */
    public static void setin(String m) {
        in=m;
    }

    /**
     * Returns a socket from a player number
     */
    public static Socket getSocket(int i) {
        return r.get(i).getSocket();
    }

    /**
     * Returns a name from a player number
     */
    public static String getName(int i) {
        return r.get(i).getName();
    }

    /**
     * Allows other classes to get the map size
     */
    public static int getMapSize() {
        return mapSize;
    }

    public static ArrayList<Character> getCharacters()  {
        return characters;
    }

    public static String now(Date date) {
        return date.getMonth()+"-"+date.getDate()+"-"+(date.getYear()+1900)+" "+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds();
    }

    public static void log(String toWrite) {
        try{
            toLog.write(now(new Date())+" "+toWrite+File.separator);
            toLog.flush();
        }catch(Exception e) {
            //e.printStackTrace();
        }
    }
}