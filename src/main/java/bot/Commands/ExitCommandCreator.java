package bot.Commands;

import bot.Bot.Session;

public class ExitCommandCreator {
    public static Command create() {
        String name = "exit";
        String description = "exit profile";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);
            bot.sessions.put(sessionId, new Session(null, session.getAction(), session.getLastOfferedQuestion(),
                    session.getAskForLoginAndPassword(), session.getToButtonsCommands()));

            return "bye";
        };
        return new Command(name, description, commandFunction);
    }
}
