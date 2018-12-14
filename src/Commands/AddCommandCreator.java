package Commands;

import Bot.BotMessages;

public class AddCommandCreator {
    public static Command create(){
        String name = "add";
        String description = "add friend";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);

            if (login.equals("")) {
                return BotMessages.needLoginAfterCommand;
            }
            if (login.equals(session.getUser().Login)) {
                return BotMessages.cantAddYourSelf;
            }
            if (!bot.userManager.isUserInDB(login)) {
                return BotMessages.noUserWithThisLogin;
            }
            bot.userManager.addFriendToUser(session.getUser().Login, login);
            return "Done";
        };
        return  new Command(name, description, commandFunction);
    }
}
