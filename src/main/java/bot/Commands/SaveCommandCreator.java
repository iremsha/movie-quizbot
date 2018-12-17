package bot.Commands;

public class SaveCommandCreator {
    public static Command create() {
        String name = "save";
        String description = "save changes";
        CommandFunction commandFunction = (bot, login, session) -> {
            bot.userManager.saveChanges();
            return "done";
        };
        return new Command(name, description, commandFunction);
    }
}
