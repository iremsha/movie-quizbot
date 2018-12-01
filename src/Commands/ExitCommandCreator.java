package Commands;

public class ExitCommandCreator {
    public static Command create(){
        String name = "exit";
        String description = "exit profile";
        CommandFunction commandFunction = (bot, login, session) -> {
            session.user = null;
            return "bye";
        };
        return  new Command(name, description, commandFunction);
    }
}
