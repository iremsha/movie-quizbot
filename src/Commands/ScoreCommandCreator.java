package Commands;

public class ScoreCommandCreator {
    public static Command create(){
        String name = "score";
        String description = "get score";
        CommandFunction commandFunction = (bot, login, session) -> {
            if (login.equals("") || login.equals(session.user.Login)){
                return String.valueOf(session.user.getScore());
            }
            if (!bot.userManager.isUserInDB(login)){
                return "No user with this login";
            }
            if (!bot.userManager.hasUserPermission(session.user.Login, login)){
                return "You can see only your friends' score";
            }
            return String.valueOf(bot.userManager.getUser(login).getScore());
        };
        return  new Command(name, description, commandFunction);
    }
}
