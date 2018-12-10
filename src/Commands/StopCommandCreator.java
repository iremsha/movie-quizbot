package Commands;

import Bot.ButtonsLists;

public class StopCommandCreator {
    public static Command create(){
        String name = "stop";
        String description = "stop playing";
        CommandFunction commandFunction = (bot, login, session) -> {
            session.action = null;
            session.toButtonsCommands = ButtonsLists.whenNoPlaying();
            return "Ok";
        };
        return  new Command(name, description, commandFunction);
    }
}
