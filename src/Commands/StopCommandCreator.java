package Commands;

import Bot.ButtonsLists;
import Bot.Session;

public class StopCommandCreator {
    public static Command create(){
        String name = "stop";
        String description = "stop playing";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);
            bot.sessions.put(sessionId, new Session(session.getUser(), null, session.getLastOfferedQuestion(),
                    session.getAskForLoginAndPassword(),ButtonsLists.whenNoPlaying() ));
            return "Ok";
        };
        return  new Command(name, description, commandFunction);
    }
}
