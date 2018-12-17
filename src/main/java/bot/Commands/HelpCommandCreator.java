package bot.Commands;

public class HelpCommandCreator {
    public static Command create() {
        String name = "help";
        String description = "get commands with description";
        CommandFunction commandFunction = (bot, login, session) -> {
            return bot.help;
        };
        return new Command(name, description, commandFunction);
    }
}
