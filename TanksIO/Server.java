import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Use "Server.methodname()" to access these methods
 * 
 */
public class Server
{
    static ArrayList<readThread> r=new ArrayList<readThread>();
    static ArrayList<Thread> r2 = new ArrayList<Thread>();
    static String in="";
    static ArrayList<Tank> players=new ArrayList<Tank>();
    static ArrayList<Tank> tanks = new ArrayList<Tank>();
    static CommandListener commandy;
    static int mapSize=100;
    static TextBox log = new TextBox();
    /**
     * This method starts the other threads, and accepts connections.
     * @param args[] port number, as a string
     */
    public static void main(String args[]) {
        System.out.println("Starting Server...");
        System.out.println("Starting command listener...");
        commandy = new CommandListener();
        //Definition of mods here
        commandy.add(new samplecommand());
        //End mods

        new File("Tanks").mkdir();
        new File("Players").mkdir(); //Makes sure that folders are set up
        new File("Mail").mkdir();

        makeTanks(); //Reads tank files and puts them in the game

        //Checks to see if a port was given as an argument
        if(args.length<1) {
            System.out.println("Umm...Port number not set..can't start server...");
            try{
                throw new Exception("Port number not set");
            }catch(Exception e) {
                e.printStackTrace();
            }
            return;
        }

        reloader w = new reloader();
        Thread w2 = new Thread(w); //This thread reloads guns and keeps players in map bounds
        w2.start();

        inThread c = new inThread();
        Thread c2 = new Thread(c); //Allows input in server terminal
        c2.start();

        readThread t;
        Thread t2; //Used when a player connects
        Tank newTank;

        ServerSocket server; //Socket used to accept connections

        boolean done=false;
        int x=-1;
        int y=-1;
        try{
            server = new ServerSocket(Integer.parseInt(args[0]));
            server.setSoTimeout(100);
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Socket client=null;
        System.out.println("Server Started.");
        System.out.println("Use /help to get command list.");
        System.out.println("Port: "+Integer.parseInt(args[0]));
        long time1=System.nanoTime();
        int ticks=0;
        int ticks2=0;
        while(!done) { //Main Server loop
            //System.gc();
            try{
                try{
                    //Remove players that are no longer connected
                    for(int i=0; i<r.size(); i++) {
                        if(r.get(i).isGoing()==false) {
                            deleteThread(i);
                            //System.out.println("DEBUG: Removed "+i);
                        }
                    }
                    client = null;
                    client = server.accept(); //Tries to accept a client connection
                    try{
                        Thread.sleep(100);
                    }catch(Exception d) {
                    }
                    if(client!=null) { //If client connects, set them up
                        System.out.println("Connected to "+client.getRemoteSocketAddress());
                        System.out.println("Setting up threads...");
                        send(client.getRemoteSocketAddress()+" connected.");
                        t=new readThread();
                        t2=new Thread(t);
                        t2.start();
                        t.setSocket(client);
                        if(r.size()<50) {
                            r.add(t);
                            r2.add(t2);
                            newTank = new Tank("NULL",0,0,0,0,0,"North",0,-3,0,0);
                            players.add(newTank);
                            players.get(r.size()-1).setNumber(r.size()-1);
                            t.setNumber(r.size()-1);
                        }
                        else { //If there are too many players, close connection
                            sendTo("Sorry, too many players!",client);
                            sendTo("You will now be kicked.",client);
                            send(client.getRemoteSocketAddress()+" disconnected (too many players)");
                            t2.stop();
                            client.close();
                        }
                    }
                }catch(SocketTimeoutException s) {

                }catch(IOException e) {
                    //e.printStackTrace();
                }
                //Commands
                if(in.equals("/stop")) {
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
                    System.out.println("/tanks - Lists all tanks in the game and their numbers");
                    System.out.println("/set <number> <number> - Puts argument 1 player in argument 2 tank (/list and /tanks)");
                    System.out.println("/map <number> - sets the map size to number. The map starts out 200x200 (Map size 100)");
                    System.out.println("/mail <name> <message> - mails message to name");
                    System.out.println("/getmail - reads mail");
                    System.out.println("/delmail - deletes mail");
                    System.out.println("/ticks - View number of ticks over the last second");
                    in="";
                }
                else if(upToSpace(in,false).equals("/set ")) {
                    x= Integer.parseInt( upToSpace( subUpToSpace(in),true ) );
                    y= Integer.parseInt( upToSpace( subUpToSpace( subUpToSpace(in) ),true ) );
                    setTank(x,new Tank(tanks.get(y)));
                }
                else if(in.equals("/tanks")) { //Shows tank list
                    System.out.println("number tankname frontarmor sidearmor reararmor reloadtime penetration viewrange camo maxgear accuracy");
                    for(int i=0; i<tanks.size(); i++) {
                        System.out.println(i+". "+tanks.get(i).getName()+" "+tanks.get(i).getFront()+" "+tanks.get(i).getSide()+" "+tanks.get(i).getRear()+" "+tanks.get(i).getReload()+" "+tanks.get(i).getPen()+" "+tanks.get(i).getViewRange()+" "+tanks.get(i).getCamo()+" "+tanks.get(i).getMaxGear()+" "+tanks.get(i).getAcc());
                    }
                    in="";
                }
                else if(in.equals("/list")) { //Shows player list
                    in="";
                    for(int i=0; i<r.size(); i++) {
                        System.out.println(i+". "+r.get(i).getSocket().getRemoteSocketAddress()+" "+r.get(i).getName()+" "+r.get(i).getTank().getName()+" "+r.get(i).getTank().getX()+","+r.get(i).getTank().getY()+ " "+r.get(i).getNumber()+" "+r.get(i).getTank().getNumber());
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
                else if(in.length()>1 && in.substring(0,1).equals("/")) {
                    System.out.println("Invalid command. Use /help to get command list");
                    in="";
                }
                else if(!in.equals("")) {
                    send(in);
                    in="";
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
        }
        w2.stop(); //Closes down threads
        c2.stop();
        for(int i=0; i<r.size(); i++) {
            r2.get(i).stop();
            deleteThread(i);
        }

        try{
            server.close(); //Closes server socket
        }catch(Exception e) {

        }
        return;
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
    public static CommandListener getListener() {
        return commandy;
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
            r2.get(i).stop();
            r.get(i).getSocket().close();
        }catch(IOException e) {

        }catch(IndexOutOfBoundsException e) {

        }
        deleteThread(i);
        System.out.println("Kicked ("+i+") "+r.get(i).getName());
    }

    /**
     * Used to remove variables of disconnected user
     */
    public static void deleteThread(int i) {
        r.remove(i);
        r2.remove(i);
        players.remove(i);
        for(int w=0; w<r.size();w++) {
            r.get(w).setNumber(w);
            r.get(w).getTank().setNumber(w);
        }
    }

    /**
     * Sends a message to every connected client in the format "Server: <message>"
     */
    public static void send(String message) {
        Socket client;
        System.out.println("Server: "+message);
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
        System.out.println(message);
        for(int i=0; i<r.size(); i++) {
            client=r.get(i).getSocket();
            try{
                new DataOutputStream(client.getOutputStream()).writeUTF(message);
            }catch(IOException e) {

            }
        }
    }


    /**
     * Sets player i in tank to
     * Used by /set command
     */
    public static void setTank(int i, Tank to) {
        players.set(i,to);
        players.get(i).setNumber(i);
    }

    /**
     * Looks for tank files and has another method read them
     */
    public static void makeTanks() {
        for(int i=0; i<100; i++) {
            if(new File("Tanks/"+Integer.toString(i)+".txt").canRead()==true) {
                readTank(i);
            }
        }
    }

    /**
     * Returns a list of all of the tanks in the game
     */
    public static ArrayList<Tank> getTanks() {
        return tanks;
    }

    /**
     * Used to read a tank file and add it to tank list
     */
    public static void readTank(int i) {
        try{
            Scanner in = new Scanner(new File("Tanks/"+Integer.toString(i)+".txt"));
            tanks.add(new Tank(in.next(),in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt(),in.nextInt(),"North",in.nextDouble(),in.nextDouble(),in.nextInt(),in.nextDouble()));
        }catch(Exception e) {

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
        }catch(IOException e) {

        }
    }

    /**
     * Used by readThread to send chat messages
     */
    public static void send(Socket from, String message, String m2) {
        Socket client;
        System.out.println(from.getRemoteSocketAddress()+"("+m2+")"+": "+message);
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

    /**
     * Registers a player
     * @param u Username
     * @param p Password
     */
    public static String authenr(String u, String p) {
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
                FileWriter outf = new FileWriter(new File("Players/"+u+".txt"));
                outf.write(p+" ");
                outf.write("M4Sherman ");
                outf.write("Hellcat ");
                outf.write("Tiger ");
                outf.flush();
                outf.close();
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
            if(new File("Players/"+u+".txt").canRead()==true) { //Adds the tanks that the player owns into an arraylist
                Scanner in = new Scanner(new File("Players/"+u+".txt"));
                if(in.next().equals(p)) {
                    ArrayList<Tank> forplayer = new ArrayList<Tank>();
                    while(in.hasNext()) {
                        use=in.next();
                        for(int i=0; i<tanks.size(); i++) {
                            if(use.equals(tanks.get(i).getName())) {
                                forplayer.add(tanks.get(i));
                            }
                        }
                    }
                    Player newplayer = new Player();
                    newplayer.setTanks(forplayer);
                    to.setPlayer(newplayer);
                    send(u+" logged in");
                    System.out.println("Password: "+p);
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
     * Get player tanks
     */
    public static ArrayList<Tank> getPlayers() {
        return players;
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
}