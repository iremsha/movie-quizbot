
public class StartCommandCreator {
    public static Command create(){
        String name = "start";
        String description = "start chat bot";
        CommandFunction commandFunction = (bot, login, session) -> {
            bot.sessions.put(session.Id, new Session());
            return bot.instruction;
        };
        return  new Command(name, description, commandFunction);
    }
}
