public class StopCommandCreator {
    public static Command create(){
        String name = "stor";
        String description = "stop playing";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
