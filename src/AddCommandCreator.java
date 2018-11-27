public class AddCommandCreator {
    public static Command create(){
        String name = "add";
        String description = "add friend";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
