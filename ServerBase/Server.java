import java.util.ArrayList;
import java.io.*;
public class Server implements Runnable, InputUser
{
    private ServerSocketHandler connectHandler;

    private int dispLabelCount = 20;
    private ServerGUI gui = new ServerGUI(dispLabelCount);
    private TerminalPanel term = gui.term2;;
    private DataDisplayPanel disp = gui.dataDisplay;
    
    private boolean usingTerm = false;
    
    private ArrayList<User> Users = new ArrayList<User>();
    private int maxUsers = 50;
    
    private boolean going = false;
    private boolean halted = false;
    
    private int tps = 0;
    private double tickTime = 0;
    
    private Logger logger = new Logger();
    private boolean debug = false;
    private int totalBytesSent = 0;
    private long startTime = System.nanoTime();
    private int targetTps = 20;
    
    public static void main(String args[]) {
        Server s = new Server();
        if(args.length>0) {
            if(args[0].equals("debug")) {
                s.inputText("/debug");
            }
        }
        s.openTerm();
        s.start();
    }
    
    public void calcTotalBytesSent() {
        totalBytesSent = 0;
        for(int i=0; i<Users.size(); i++) {
            SocketHandler s = Users.get(i).getSocketHandler();
            totalBytesSent+=s.getSize();
        }
    }
    
    public boolean isHalted() {
        return halted;
    }

    public void start() {
        new Thread(this).start();
    }

    public void setMaxPlayers(int x) {
        maxUsers=x;
    }

    public void stop() {
        going=false;
    }

    public boolean listIsFull() {
        return Users.size()>=maxUsers;
    }

    public void addUser(SocketHandler s) {
        User p = new User(s,this);
        MainMenu m = new MainMenu();
        m.setUser(p);
        p.setHandler(m);
        Users.add(p);
        out("New User Connected from "+s.getAddress());
    }
    
    public int getUserCount() {
        return Users.size();
    }
    
    public void startSocketHandler() {
        if(!connectHandler.isFinished()) {
            out("Could not start server socket handler: It's already started!");
            return;
        }
        
        try{
            long time2 = System.nanoTime();
            connectHandler.setup();
            debug("Took "+((double)(System.nanoTime()-time2)/1000000000)+" Seconds to start the server socket handler.");
            connectHandler.setTimeout(1);
            connectHandler.setWaitTime(100);
        }catch(Exception e) {
            out("Could not start server socket handler");
            debug(e.getStackTrace()+"");
            return;
        }
    }
    
    public void createSocketHandler() {
        try{
            connectHandler = new ServerSocketHandler(this);
        }catch(Exception e) {
            out("Could not create ServerSocketHandler");
            debug(e.getStackTrace()+"");
        }
    }

    public synchronized void run() {
        out("Starting server...");
        
        new File("Files").mkdir();
        
        long time1 = System.nanoTime();
        
        createSocketHandler();
        
        startSocketHandler();
        
        out("Server is being hosted on "+getAddress());
        going=true;
        debug("Took "+((double)(System.nanoTime()-time1)/1000000000)+" Seconds to start the server.");
        time1=System.nanoTime();
        int tps2=0;
        while(going) {
            long tickStart = System.nanoTime();
            try{
                //if(!halted) Thread.sleep(0);
                if(!halted) Thread.sleep(1000 / targetTps);
                else Thread.sleep(1000 / 1);
            }catch(Exception e) {

            }
            
            tickStart = System.nanoTime();
            
            //System.gc();
            
            removeUnconnected();
            
            if(usingTerm) {
                disp.setText(0, "Users: " + Users.size() );
                disp.setText(1, "Host Address: " + getAddress() );
                disp.setText(2, "TPS: " + tps );
                disp.setText(3, "Halted: " + halted );
                disp.setText(4, "Tick Time: " + tickTime + " ms" );
                //disp.setText(5, "Matches: " + matches.size() );
                //disp.setText(6, "Players in MatchMaker: "+ mm.getQueueSize() );
                disp.setText(7, "Bytes Sent: " + totalBytesSent );
                disp.setText(8, "Free Mem: " + Runtime.getRuntime().freeMemory() );
                disp.setText(9, "Total Mem: " + Runtime.getRuntime().totalMemory() );
                disp.setText(10, "Used Mem: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ) );
                disp.setText(11, "Used %: " + (100 * (double)(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / (double)Runtime.getRuntime().totalMemory()));
                disp.setText(12, "Uptime: "+( (double) ( System.nanoTime() - startTime ) / 1000000000.0 ) + "s");
            }

            if(!halted) {
                //mm.run();
                //endMatches();
            }
            
            if(!logger.canWrite) logger.reopen();

            tickTime = ( ( System.nanoTime() - (double)tickStart ) ) / 1000000;
            
            tps2++;
            if(System.nanoTime() - time1 >= 1000000000) {
                tps=tps2;
                time1=System.nanoTime();
                tps2=0;
                calcTotalBytesSent();
            }
        }
        out("Closing Server...");
        sendAll("Closing Server...");
        connectHandler.stop();
        
        for(int i=0; i<Users.size(); i++) {
            Users.get(i).disconnect();
        }
        
        removeUnconnected();

        logger.close();
        
        closeTerm();
        
        out("Server Closed");
    }

    public void removeUnconnected() {
        for(int i=0; i<Users.size(); i++) {
            SocketHandler p = Users.get(i).getSocketHandler();
            if(p.isConnected()==false) {
                Users.remove(i);
            }
        }
    }

    public void inputText(String i) {
        logger.log("SERVER ADMIN: "+i);
        if(i.equals("/help")) {
            out("/halt - halt the server");
            out("/mmon - turn the match maker on and off");
            out("/stop - stop the server");
            out("/debug - enable debug mode");
            out("/open - open server socket, if it is not already open");
            out("/close - close server socket, stop accepting connections");
            out("/ttps <int> - set target tps for server");
        }
        else if(i.equals("/stop")) {
            stop();
        }
        else if(i.equals("/halt")) {
            halted=!halted;
            if(halted) {
                out("Server activity halted!");
                sendAll("Server activity halted!");
            }
            else {
                out("Server activity no longer halted");
                sendAll("Server activity no longer halted");
            }
        }
        else if(i.equals("/debug")) {
            debug=!debug;
            debug(debug+"");
        }
        else if(i.equals("/close")) {
            out("Stopping server socket handler...");
            connectHandler.stop();
        }
        else if(i.equals("/open")) {
            out("Opening server socket handler...");
            startSocketHandler();
        }
        else if(Command.is("/ttps",i)) {
            int x;
            try{
                x = Integer.parseInt( Command.getArgs(i).get(1));
            }catch(Exception e) {
                out("Please enter an integer!");
                return;
            }
            targetTps=x;
            out("Target tps set to "+x);
        }
        else sendAll("ADMIN",i);
    }

    public void sendAll(User u, String s) {
        out("["+u.getName()+"] "+s);
        for(int i=0; i<Users.size(); i++) {
            Users.get(i).send("["+u.getName()+"] "+s);
        }
    }

    public void sendAll(String s) {
        out("[SERVER] "+s);
        for(int i=0; i<Users.size(); i++) {
            Users.get(i).send("[SERVER] "+s);
        }
    }
    
    public void sendAll(String t, String s) {
        out("[" + t + "] " + s);
        for(int i=0; i<Users.size(); i++) {
            Users.get(i).send("[" + t + "] " + s);
        }
    }
    
    public void debug(String o) {
        if(debug) {
            out("[DEBUG] "+o);
        }
    }

    public void out(String o) {
        if(usingTerm) {
            term.write(o);
        }
        logger.log(o);
    }

    public void openTerm() {
        //term=new Terminal();
        //term.setUser(this);
        //term.setTitle("Server Terminal");
        
        //disp = new DataDisplay(dispLabelCount);
        
        gui.frame.setVisible(true);
        gui.user = this;
        gui.term2.setUser(this);
        
        usingTerm=true;
    }

    public void closeTerm() {
        if(!usingTerm) return;
        
        //term.setVisible(false);
        //disp.setVisible(false);
        gui.frame.setVisible(false);
        usingTerm=false;
    }

    public int getPort() {
        if(connectHandler==null) {
            return -1;
        }
        return connectHandler.getPort();
    }

    public String getAddress() {
        try{ 
            return ""+connectHandler.getSocket().getInetAddress().getLocalHost()+":"+getPort();
        }catch(Exception e) {
            return null;
        }
    }

    public ServerSocketHandler getServerSocketHandler() {
        return connectHandler;
    }
}