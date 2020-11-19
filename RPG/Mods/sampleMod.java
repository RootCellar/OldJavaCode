package Mods;  
import com.Darian.RPG.Character;
import com.Darian.RPG.Mod;
import com.Darian.RPG.readThread;
import com.Darian.RPG.*;
/**
 * Just an example mod class
 */
public class sampleMod implements Mod
{
    /**
     * The constructor allows you to initialize your command mod.
     * You can do nothing, print a message like "samplemod started!",
     * or anything else needed to initialize the mod.
     */
    public sampleMod() {
        System.out.println("Sample mod constructed");
    }
    
    public void tick() {
        
    }
    
    public void use(Character user, Character target, Ability ability) {
        
    }
    
    public void init() {
        System.out.println("Setting up the sample mod, even though it doesn't need to initialize...");
    }

    /**
     * Called when a player executes a command
     * This is where you add commands
     */
    public void command(String m, readThread from) {
        /**
         * Sample command
         * When the player uses "/sayhi", it tells the player that they said hi,
         * and makes the player send "Hello! I am testing out the /sayhi command!"
         */
        if(m.equals("/sayhi")) {
            Server.sendTo("You said hi!",from.getSocket());
            Server.send(from.getSocket(), "Hello! I am testing out the /sayhi command!", from.getName());
        }
        /**
         * There can be more than one command!
         * This could be a huge list of commands!
         */
        
        /**
         * This command shows the player list to the player upon using /list
         */
        if(m.equals("/list")) {
            for(int i=0; i<Server.getThreads().size(); i++) {
                Server.sendTo((i+1)+". "+Server.getThreads().get(i).getName(),from.getSocket());
            }
        }
        
        /**
         * /emote command!
         * Added just for fun
         */
        if(Server.upToSpace(m,true).equals("/emote")) {
            Server.sendAll(from.getName()+" "+Server.subUpToSpace(m));
        }
        
        /**
         * You can add your commands to /help like this!
         * Now, players can see the /list and /sayhi commands.
         * Without this, the players won't know about these commands unless told about them.
         */
        if(m.equals("/help")) {
            Server.sendTo("Supplied by the SampleCommand mod",from.getSocket());
            Server.sendTo("/sayhi - Says hi!",from.getSocket());
            Server.sendTo("/list - lists players",from.getSocket());
            Server.sendTo("/emote <message> - Sends a message in the format '<yourname> <message>'",from.getSocket());
        }
    }
}