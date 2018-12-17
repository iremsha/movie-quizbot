package bot.Commands;

import bot.User.UserInfo;

public class MoviesCommandCreator {
    public static Command create() {
        String name = "movies";
        String description = "get movies you or your friend know";
        CommandFunction commandFunction = (bot, login, session) -> {
            return CommandsCreator.getUserInfoCommand(UserInfo.Movies, bot, login, session);

        };
        return new Command(name, description, commandFunction);
    }
}
