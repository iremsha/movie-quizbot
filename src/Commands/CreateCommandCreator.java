package Commands;

public class CreateCommandCreator {
    public static Command create(){
        String name = "create";
        String description = "create profile";
        CommandFunction commandFunction = (bot, argument, session) -> {
//            где то здесь добавить проверку не взяли ли логин пока придумывался пароль
//            if (login.isEmpty()){
//                return "Empty login";
//            }
//            if (bot.userManager.isUserInDB(login)){
//                return "This login has already taken";
//            }
//            session.enteredLogin = login;
//            session.askForPassword = true;
//            return "Enter password";

            return bot.identifyUser("create", argument, session);
        };
        return  new Command(name, description, commandFunction);
    }
}
