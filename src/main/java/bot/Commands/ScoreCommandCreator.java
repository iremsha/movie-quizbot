package bot.Commands;

import bot.User.UserInfo;

public class ScoreCommandCreator {
    public static Command create() {
        String name = "score";
        String description = "get score";
        CommandFunction commandFunction = (bot, login, session) -> {
            return CommandsCreator.getUserInfoCommand(UserInfo.Score, bot, login, session);

        };
        return new Command(name, description, commandFunction);
    }
}
