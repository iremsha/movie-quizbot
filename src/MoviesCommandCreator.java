public class MoviesCommandCreator {
    public static Command create(){
        String name = "movies";
        String description = "get movies you or your friend know";
        CommandFunction commandFunction = (bot, login, session) -> {
            return "not implemented";
        };
        return  new Command(name, description, commandFunction);
    }
}
