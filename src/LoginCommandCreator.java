public class LoginCommandCreator {
    public static Command create(){
        String name = "login";
        String description = "log in";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
