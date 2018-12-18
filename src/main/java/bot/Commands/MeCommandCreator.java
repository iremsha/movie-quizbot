package bot.Commands;

public class MeCommandCreator {
    public static Command create() {
        String name = "me";
        String description = "check your login";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            return bot.sessions.get(sessionId).getUser().getLogin();
        };
        return new Command(name, description, commandFunction);
    }
}
