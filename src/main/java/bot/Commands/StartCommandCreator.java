package bot.Commands;

import bot.Bot.Session;

import java.util.Arrays;

public class StartCommandCreator {
    public static Command create() {
        String name = "start";
        String description = "start chat bot";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);
            var toButtonsCommands = Arrays.asList("login", "create");
            bot.sessions.put(sessionId, new Session(session.getUser(), session.getAction(),
                    session.getLastOfferedQuestion(), session.getAskForLoginAndPassword(), toButtonsCommands));
            return bot.instruction;
        };
        return new Command(name, description, commandFunction);
    }
}
