package Commands;

public class MeCommandCreator {
    public static Command create(){
        String name = "me";
        String description = "check your login";
        CommandFunction commandFunction = (bot, login, session) -> {
            return session.user.Login;
        };
        return  new Command(name, description, commandFunction);
    }
}
