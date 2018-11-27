
public class CreateCommandCreator {
    public static Command create(){
        String name = "start";
        String description = "start chat bot";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
