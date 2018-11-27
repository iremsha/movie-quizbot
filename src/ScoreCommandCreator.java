public class ScoreCommandCreator {
    public static Command create(){
        String name = "score";
        String description = "get score";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
