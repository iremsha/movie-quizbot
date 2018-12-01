package Commands;

import java.util.Arrays;

public class StartCommandCreator {
    public static Command create(){
        String name = "start";
        String description = "start chat bot";
        CommandFunction commandFunction = (bot, login, session) -> {
            //bot.sessions.put(session.Id, new Session());
            session.availableCommands = Arrays.asList("login", "create");
            return bot.instruction;
        };
        return  new Command(name, description, commandFunction);
    }
}
