public class MainMenu extends Menu implements UserHandler
{
    //User user;
    //Server server;

    public void inputText(String i) {
        if(i.equals("/help")) {
            user.send("/joingame - join the game");
            user.send("/quit - disconnect");
            user.send("/setname <string> - set your name");
            user.send("/say <string> - chat, send a message");
        }

        if(i.equals("/quit")) {
            user.send("Bye!");
            user.disconnect();
        }

        if(Command.is("/setname",i)) {

            if( CommandHelper.isValid( i.substring(9), 3, 10 ) ) {
                user.setName(i.substring(9));
                user.send("Name set to "+i.substring(9));
            }
            else {
                user.send("Name is invalid");
                user.send("Name must be 3 - 10 characters in length");
                user.send("Name can only have letters and numbers");
            }
        }

        if(Command.is("/say",i)) {
            server.sendAll(user,i.substring(5));
        }
    }

    public void intro() {
        user.send("Welcome!");
        user.send("Use /help for a list of commands");
    }
}