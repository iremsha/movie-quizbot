public class PlayCommandCreator {
    public static Command create(){
        String name = "play";
        String description = "play quiz";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
