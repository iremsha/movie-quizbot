public class FriendsCommandCreator {
    public static Command create(){
        String name = "friend";
        String description = "get friends";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
