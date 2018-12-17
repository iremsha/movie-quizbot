package bot.Commands;

public class LoginCommandCreator {
    public static Command create() {
        String name = "login";
        String description = "log in";
        CommandFunction commandFunction = (bot, argument, session) -> {
            return bot.identifyUser("login", argument, session);

        };
        return new Command(name, description, commandFunction);
    }
}
