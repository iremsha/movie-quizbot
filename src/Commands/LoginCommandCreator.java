package Commands;

public class LoginCommandCreator {
    public static Command create(){
        String name = "login";
        String description = "log in";
        CommandFunction commandFunction = (bot, login, session) -> {
//            продумать что с данными если зайти в один акк с разных тг акк

//            + обавить запрос на логин
            if (login.isEmpty()){
                return "Empty login";
            }
            session.user = null;
            if (!bot.userManager.isUserInDB(login)) {
                return "No users with this login. Try again";
            }
            session.enteredLogin = login;
            session.askForPassword = true;
            return "Enter password"; // should be in spec method
//                return "you've log in as " + session.user.Login;
        };
        return  new Command(name, description, commandFunction);
    }
}
