package Commands;
public class AddCommandCreator {
    public static Command create(){
        String name = "add";
        String description = "add friend";
        CommandFunction commandFunction = (bot, login, session) -> {
            if (login.equals("")) {
                return "Need login after command";
            }
            if (login.equals(session.user.Login)) {
                return "You can't add yourself";
            }
            if (!bot.userManager.isUserInDB(login)) {
                return "No user with this login";
            }
            bot.userManager.addFriendToUser(session.user.Login, login);
            return "Done";
        };
        return  new Command(name, description, commandFunction);
    }
}
