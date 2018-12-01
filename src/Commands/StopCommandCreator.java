package Commands;

public class StopCommandCreator {
    public static Command create(){
        String name = "stor";
        String description = "stop playing";
        CommandFunction commandFunction = (bot, login, session) -> {
            session.playing = false;
            return "Ok";
        };
        return  new Command(name, description, commandFunction);
    }
}
