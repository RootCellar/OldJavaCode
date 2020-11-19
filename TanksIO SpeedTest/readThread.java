import java.net.*;
import java.io.*;
import java.util.Random;
/**
 * This class is run as a separate thread to handle player commands
 */
public class readThread implements Runnable
{
    Socket readFrom = null;
    String name="";
    int number=-1;
    Tank player;
    Tank other;
    String use="";
    int which;
    double dis;
    boolean isLoggedIn=false;
    Player user;
    int gear=1;
    boolean isGoing=true;
    public void run() {
        DataInputStream in=null;
        String in2="";
        while(readFrom==null || number==-1) {
            try{
                Thread.sleep(100);
            }catch(Exception e) {

            }
        }
        try{
            in = new DataInputStream(readFrom.getInputStream());
        }catch(IOException e) {

        }
        player=Server.getPlayers().get(number);
        while(!isLoggedIn) { //Lets a client log in
            try{
                try{
                    Thread.sleep(100);
                }catch(Exception e) {

                }
                try{
                    Server.sendTo("Log in or register?",readFrom);
                    Server.sendTo("/login or /register",readFrom);
                    in2=in.readUTF();
                    if(in2.equals("/register")) {
                        Server.sendTo("Username: ",readFrom);
                        in2=in.readUTF();
                        Server.sendTo("Password: ",readFrom);
                        use=in.readUTF();
                        name=Server.authenr(in2,use);
                        if(name.equals("Register")) {
                            Server.sendTo("Successfully registered",readFrom);
                            Server.sendTo("Username: "+in2,readFrom);
                            Server.sendTo("Password: "+use,readFrom);
                            Server.sendTo("Please enter username and password again to log in",readFrom);
                        }
                        else if(name.equals("Wrong password")) {
                            Server.sendTo("Wrong password",readFrom);
                        }
                        else {
                            Server.sendTo(name,readFrom);
                        }
                    }
                    if(in2.equals("/login")) {
                        Server.sendTo("Username: ",readFrom);
                        in2=in.readUTF();
                        Server.sendTo("Password: ",readFrom);
                        use=in.readUTF();
                        name=Server.authen(in2,use,this);
                        if(name.equals("Logged in")) {
                            isLoggedIn=true;
                            name=in2;
                        }
                        else if(name.equals("Wrong password")) {
                            Server.sendTo("Wrong password",readFrom);
                        }
                        else {
                            Server.sendTo(name,readFrom);
                        }
                    }
                }catch(IOException e) {
                    break;
                }
            }catch(Exception e) {
                //Server.send("Server error");
                //e.printStackTrace();
            }
        }
        player=Server.getPlayers().get(number);
        Server.sendTo("Logged in",readFrom);
        Server.sendTo("Pick a tank or use other commands",readFrom);
        Server.sendTo("(like /help)",readFrom);
        /**
         * Before the player selects a tank
         * They can talk, send mail, and see tank lists
         */
        while(player.getName().equals("NULL")) {
            try{
                player=Server.getPlayers().get(number);
                try{
                    Thread.sleep(100);
                }catch(Exception e) {

                }
                try{
                    in2=in.readUTF();
                    player=Server.getPlayers().get(number);
                    if(in2.equals("/help")) {
                        Server.sendTo("/tanks - lists your tanks",readFrom);
                        Server.sendTo("/alltanks - lists all tanks",readFrom);
                        Server.sendTo("/say <message> - talk in the chat",readFrom);
                        Server.sendTo("/pick <number> - use /tanks to list your tanks, and then enter the number of the tank you want to use",readFrom);
                        Server.sendTo("/mail <name> <message> - Mail message to name. If 'Server' is the name, it will be sent to the server",readFrom);
                        Server.sendTo("/getmail - reads your mail",readFrom);
                        Server.sendTo("/delmail - deletes your mail",readFrom);
                    }
                    if(Server.upToSpace(in2,false).equals("/mail ")&& in2.length()>6 && in2.indexOf("END")==-1) {
                        Server.sendTo(Server.mail(in2.substring(in2.indexOf(" ")+1)+" from "+name),readFrom);
                    }
                    if(in2.equals("/getmail")) {
                        Server.sendTo(Server.getMail(name),readFrom);
                    }
                    if(in2.equals("/delmail")) {
                        Server.delMail(name);
                        Server.sendTo("Mail deleted",readFrom);
                    }
                    if(in2.equals("/tanks")) { //Shows player tanks they own
                        for(int i=0; i<user.getTanks().size(); i++) {
                            Server.sendTo(i+". "+user.getTanks().get(i).getName(),readFrom);
                        }
                    }
                    if(in2.equals("/alltanks")) { //Shows player of all tanks available on the server
                        for(int i=0; i<Server.getTanks().size(); i++) {
                            Server.sendTo(i+". "+Server.getTanks().get(i).getName(),readFrom);
                        }
                    }
                    if(Server.upToSpace(in2,false).equals("/say ")) { //Allows player to send a message
                        Server.send(readFrom,Server.subUpToSpace(in2),name);
                    }
                    if(Server.upToSpace(in2,false).equals("/pick ")) { //Allows player to pick a tank
                        which = Integer.parseInt(Server.subUpToSpace(in2));
                        if(which >= user.getTanks().size() || which < 0) {
                            Server.sendTo("Invalid tank!",readFrom);
                        }
                        else { //Puts the player in the tank
                            Server.setTank(number,new Tank(user.getTanks().get(which)));
                            //player = new Tank(user.getTanks().get(which));
                            player.setNumber(number);
                            Server.sendTo("Tank set",readFrom);
                            Server.send(name+" has joined the fight!");
                            //System.out.println(name + " has joined the fight!");
                        }
                    }
                }catch(IOException e) {
                    break;
                }
                player=Server.getPlayers().get(number);
                Server.getListener().command(in2,this);
            }catch(Exception e) {
                //Server.send("Server error");
                //e.printStackTrace();
            }
        }

        try{
            player=Server.getPlayers().get(number);
        }catch(IndexOutOfBoundsException e) {

        }
        Server.sendTo("Tank Selected. Roll Out!",readFrom);
        Server.sendTo("Use /help to see the list of commands",readFrom);
        //Puts player in random spot on map
        player.setX( new Random().nextInt(Server.getMapSize()*2)-Server.getMapSize() );
        player.setY( new Random().nextInt(Server.getMapSize()*2)-Server.getMapSize() );
        player.setGear(1);
        while(true) {
            try{
                in2=in.readUTF();
                player=Server.getPlayers().get(number);
                try{
                    Thread.sleep(100); //Stops players from overloading server with commands (DDOS Attacks Denied!)
                }catch(Exception e) {

                }
                if(player.isAlive()==false) { //Player can't do anything when dead
                    Server.sendTo("You are dead! Rejoin!",readFrom);
                    in2="";
                }
                else if(in2.equals("getr")) { //Lets player see reload time
                    use=String.valueOf(player.getReload());
                    Server.sendTo(use,readFrom);
                }
                else if(in2.equals("/help")) {
                    Server.send("/help - Displays command list",readFrom);
                    Server.send("w - drive north",readFrom);
                    Server.send("s - drive south",readFrom);
                    Server.send("a - drive west",readFrom);
                    Server.send("d - drive east",readFrom);
                    Server.send("fw - face north",readFrom);
                    Server.send("fs - face south",readFrom);
                    Server.send("fa - face west",readFrom);
                    Server.send("fd - face east",readFrom);
                    Server.send("spot - spot enemies",readFrom);
                    Server.send("loc - view location (x,y)",readFrom);
                    Server.send("f,fire,shoot - shoot at the closest available enemy",readFrom);
                    Server.send("/mytank - shows tank info",readFrom);
                    Server.send("/g <number> - Sets the gear on your tank. Changes how fast you move",readFrom);
                }
                else if(in2.length()>0 && in2.substring(0,1).equals("g")) { //Allows player to change gears
                    which=Integer.parseInt(Server.subUpToSpace(in2));
                    if(which>player.getMaxGear()) {
                        Server.send("Your tank can't go that fast",readFrom);
                    }
                    else if(which<1) {
                        Server.send("Would you really want to do that?",readFrom);
                    }
                    else {
                        gear=which;
                        player.setGear(which);
                        Server.sendTo("Gear changed",readFrom);
                    }
                }
                else if(in2.equals("/mytank")) { //Lets player see tank stats
                    Server.sendTo(player.toString(),readFrom);
                }
                //Allows player to face different directions
                else if(in2.equals("fw")) {
                    player.setFacing("North");
                    Server.sendTo("now facing north",readFrom);
                }
                else if(in2.equals("fs")) {
                    player.setFacing("South");
                    Server.sendTo("now facing south",readFrom);
                }
                else if(in2.equals("fa")) {
                    player.setFacing("West");
                    Server.sendTo("now facing west",readFrom);
                }
                else if(in2.equals("fd")) {
                    player.setFacing("East");
                    Server.sendTo("now facing east",readFrom);
                }
                //Lets player drive tank around, at gear speed
                else if(in2.equals("w")) {
                    player.setY(player.getY()+gear);
                    Server.sendTo("Drove north",readFrom);
                }
                else if(in2.equals("s")) {
                    player.setY(player.getY()-gear);
                    Server.sendTo("Drove south",readFrom);
                }
                else if(in2.equals("a")) {
                    player.setX(player.getX()-gear);
                    Server.sendTo("Drove west",readFrom);
                }
                else if(in2.equals("d")) {
                    player.setX(player.getX()+gear);
                    Server.sendTo("Drove east",readFrom);
                }
                else if(in2.equals("spot") || in2.equals("sp")) { //Lets the player spot enemy tanks, if they can see them
                    Server.sendTo("Spotting....",readFrom);
                    for(int i=0; i<Server.getPlayers().size(); i++) {
                        if(i!=number) {
                            use=player.spotP(Server.getPlayers().get(i),getDistance(player,Server.getPlayers().get(i)));
                            if(!use.equals("")) {
                                Server.sendTo(use+" Distance: "+getDistance(player,Server.getPlayers().get(i)),readFrom);
                            }
                        }
                    }
                }
                else if(in2.equals("loc")) { //Lets the player see where they are
                    Server.sendTo(player.getX()+","+player.getY(),readFrom);
                }
                else if(in2.equals("shoot") || in2.equals("fire") || in2.equals("f")) { //Fires at closest available target
                    Server.sendTo("Attempting to shoot...",readFrom);
                    which=-1;
                    dis=0;
                    for(int i=0; i<Server.getPlayers().size(); i++) { //get 1st available target
                        other=Server.getPlayers().get(i);
                        if(player.spot(other,getDistance(player,Server.getPlayers().get(i)))==true && i!=number) {
                            dis=getDistance(player,Server.getPlayers().get(i));
                            which=i;
                        }
                    }

                    for(int i=0; i<Server.getPlayers().size(); i++) { //get closest target
                        other=Server.getPlayers().get(i);
                        if(getDistance(player,Server.getPlayers().get(i))<dis && player.spot(other,getDistance(player,Server.getPlayers().get(i)))==true && i!=number) {
                            dis=getDistance(player,Server.getPlayers().get(i));
                            which=i;
                        }
                    }
                    if(which!=-1) { //Sends message to other player that they have been shot
                        other=Server.getPlayers().get(which);
                        use=player.shoot(other);
                        //System.out.println(readFrom.getRemoteSocketAddress()+"("+name+") "+player.getName()+": "+use+" Target: "+Server.getSocket(other.getNumber()).getRemoteSocketAddress()+"("+Server.getName(other.getNumber())+") "+other.getNumber()+" "+other.getName());
                        Server.sendTo(use,readFrom);
                        if(use.equals("Shot Bounced off!")) {
                            System.out.println(readFrom.getRemoteSocketAddress()+"("+name+") "+player.getName()+": "+use+" Target: "+Server.getSocket(other.getNumber()).getRemoteSocketAddress()+"("+Server.getName(other.getNumber())+") "+other.getNumber()+" "+other.getName());
                            use="";
                            if(player.getY()>other.getY()) {
                                use+="North";
                            }
                            else if(player.getY()<other.getY()) {
                                use+="South";
                            }
                            if(player.getX()>other.getX()) {
                                use+="East";
                            }
                            if(player.getX()<other.getX()) {
                                use+="West";
                            }
                            //System.out.println(use);
                            if(!use.equals("")) {
                                Server.sendTo("Shot bounced from "+use,other.getNumber());
                            }
                            if(use.equals("")) {
                                Server.sendTo("Bounce!",other.getNumber());
                            }
                        }
                        if(use.equals("Miss!")) {
                            System.out.println(readFrom.getRemoteSocketAddress()+"("+name+") "+player.getName()+": "+use+" Target: "+Server.getSocket(other.getNumber()).getRemoteSocketAddress()+"("+Server.getName(other.getNumber())+") "+other.getNumber()+" "+other.getName());
                            use="";
                            if(player.getY()>other.getY()) {
                                use+="North";
                            }
                            else if(player.getY()<other.getY()) {
                                use+="South";
                            }
                            if(player.getX()>other.getX()) {
                                use+="East";
                            }
                            if(player.getX()<other.getX()) {
                                use+="West";
                            }
                            //System.out.println(use);
                            if(!use.equals("")) {
                                Server.sendTo("Shot missed from "+use,other.getNumber());
                            }
                            if(use.equals("")) {
                                Server.sendTo("What the...a tank missed and was right next to you...",other.getNumber());
                            }
                        }
                        if(Server.upToSpace(use,false).equals("Enemy ")) { //Player has killed enemy player
                            Server.sendTo("You have been destroyed!",other.getNumber());
                            System.out.println(readFrom.getRemoteSocketAddress()+"("+name+") "+player.getName()+": "+use+" Target: "+Server.getSocket(other.getNumber()).getRemoteSocketAddress()+"("+Server.getName(other.getNumber())+") "+other.getNumber()+" "+other.getName());
                        }
                    }
                    else {
                        Server.sendTo("No target available",readFrom);
                    }
                }
                else if(in2.length()>=1 && !in2.substring(0,1).equals("/")){ //If the player enters a message
                    Server.send(readFrom,in2,name);
                }
                Server.getListener().command(in2,this); //Passes command and class instance to mods, so that they can function
            }catch(IOException e) { //Connection closed, player is quitting
                //System.out.println(readFrom.getRemoteSocketAddress()+ " ("+name+") Disconnected");
                Server.send(readFrom.getRemoteSocketAddress()+"("+name+") Disconnected");
                //Server.deleteThread(number);
                isGoing=false;
                try{
                    readFrom.close();
                }catch(IOException f) {

                }
                break;
            }catch(Exception e) {
                //Server.send("Server error");
                //e.printStackTrace();
            }
        }
    }

    public boolean isGoing() {
        return isGoing;
    }

    /**
     * Gets the distance between two tanks
     */
    public static double getDistance(Tank one, Tank two) {
        int x1=one.getX();
        int y1=one.getY();
        int x2=two.getX();
        int y2=two.getY();
        return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
    }

    public Tank getTank() {
        return player;
    }

    public void setTank(Tank to) {
        player = new Tank(to);
    }

    public void setNumber(int num) {
        number=num;
    }

    public void setSocket(Socket use) {
        readFrom=use;
    }

    /**
     * Returns the socket connection to the player
     */
    public Socket getSocket() {
        return readFrom;
    }

    /**
     * Returns the player's name
     */
    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void setPlayer(Player newplayer) {
        user=newplayer;
    }

    /**
     * Returns the object containing the player info
     */
    public Player getUser() {
        return user;
    }
}