package Commands;

public class CreateCommandCreator {
    public static Command create(){
        String name = "create";
        String description = "create profile";
        CommandFunction commandFunction = (bot, argument, session) -> {
            return bot.identifyUser("create", argument, session);
        };
        return  new Command(name, description, commandFunction);
    }
}
