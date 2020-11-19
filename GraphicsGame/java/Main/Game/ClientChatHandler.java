public class ClientChatHandler implements InputUser
{
    Game game;
    public ClientChatHandler(Game g) {
        game=g;
    }

    public void inputText(String s) {
        try{
            System.out.println(s);
            game.server.send(s);
        }catch(Exception e) {
            //e.printStackTrace();
        }
    }

    public void inputObject(Object o) {

    }
}