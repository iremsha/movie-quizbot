package bot.Commands;

import bot.Bot.BotMessages;

public class AddCommandCreator {
    public static Command create() {
        String name = "add";
        String description = "add friend";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);

            if (login.equals("")) {
                return BotMessages.needLoginAfterCommand;
            }
            if (login.equals(session.getUser().getLogin())) {
                return BotMessages.cantAddYourSelf;
            }
            if (!bot.userManager.isUserInDB(login)) {
                return BotMessages.noUserWithThisLogin;
            }
            bot.userManager.addFriendToUser(session.getUser().getLogin(), login);
            return "Done";
        };
        return new Command(name, description, commandFunction);
    }
}