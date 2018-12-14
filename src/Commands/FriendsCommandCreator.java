package Commands;

import User.UserInfo;

public class FriendsCommandCreator {
    public static Command create(){
        String name = "friends";
        String description = "get friends";
        CommandFunction commandFunction = (bot, login, session) -> {
            return CommandsCreator.getUserInfoCommand(UserInfo.Friends, bot, login, session);
        };
        return  new Command(name, description, commandFunction);
    }
}
