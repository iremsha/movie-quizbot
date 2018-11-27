import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandsCreator {
    public static List<Command> getBotCommands(){
        List<Command> commands = new ArrayList<>();
//        List<String> names = Arrays.asList("start", "help", "stop"...);
//        List<String> descriptions;
        commands.add(StartCommandCreator.create());
        commands.add(AddCommandCreator.create());
        commands.add(StartCommandCreator.create());
        commands.add(StartCommandCreator.create());
        commands.add(StartCommandCreator.create());
        commands.add(StartCommandCreator.create());
        commands.add(StartCommandCreator.create());

        CommandFunction startFunc = (bot, login, session) -> {
            bot.sessions.put(session.Id, new Session());
            return bot.instruction;
        };
        CommandFunction helpFunc = (bot, login, session) -> {
            return "not implemented";
        };
        CommandFunction createFunc = (bot, login, session) -> {
            if (login.isEmpty()){
                return "Empty login";
            }
            if (bot.userManager.isUserInDB(login)){
                return "This login has already taken";
            }
            session.enteredLogin = login;
            session.askForPassword = true;
            return "Enter password";
        };
        CommandFunction loginFunc = (bot, login, session) -> {
            if (login.isEmpty()){
                return "Empty login";
            }
            session.user = null;
            if (!bot.userManager.isUserInDB(login)) {
                return "No users with this login. Try again";
            }
//                session.user = bot.userManager.getUser(argument);
            session.enteredLogin = login;
            session.askForPassword = true;
            return "Enter password"; // should be in spec method
//                return "you've log in as " + session.user.Login;
        };
        CommandFunction scoreFunc = (bot, login, session) -> {
            if (login.equals("") || login.equals(session.user.Login)){
//            return Integer.toString(session.user.score);
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
        CommandFunction moviesFunc = (bot, login, session) -> {
            return "not implemented";
        };
        CommandFunction friendsFunc = (bot, login, session) -> {
            return "not implemented";
        };
        CommandFunction addFunc = (bot, login, session) -> {
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
        CommandFunction playFunc = (bot, login, session) -> {
            session.playing = true;
            String firstQuestion = bot.quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = firstQuestion;
            return bot.quizBot.getInstruction() + "\n" + firstQuestion;
        };
        CommandFunction meFunc = (bot, login, session) -> {
            return session.user.Login;
        };
        CommandFunction stopFunc = (bot, login, session) -> {
            session.playing = false;
            return "Ok";
        };
        CommandFunction exitFunc = (bot, login, session) -> {
            session.user = null;
            return "bye";
        };
        CommandFunction saveFunc = (bot, login, session) -> {
            bot.userManager.saveChanges();
            return "done";
        };


        commands.add(new Command("start", "", startFunc));
        commands.add(new Command("help", "get help", helpFunc));
        commands.add(new Command("create", "create profile", createFunc));
        commands.add(new Command("login", "log in to profile", loginFunc));
        commands.add(new Command("score", "", scoreFunc));
        commands.add(new Command("movies", "", moviesFunc));
        commands.add(new Command("friends", "", friendsFunc));
        commands.add(new Command("add", "add someone to friends", addFunc));
        commands.add(new Command("play", "play quiz", playFunc));
        commands.add(new Command("me", "", meFunc));
        commands.add(new Command("stop", "", stopFunc));
        commands.add(new Command("exit", "", exitFunc));
        commands.add(new Command("save", "", saveFunc));


        return commands;
    }
    public static HashMap<String, Command> getCommandsHashMap(){
        var commands = getCommands();
        var hashMap = new HashMap<String, Command>();
        for (var command:commands){
            hashMap.put(command.Name, command);
        }
        return hashMap;
    }

    public static String getBotHelp(){
        return "";
    }
//        case "/movies": //later add watch friends' movies
//            return getString(session.user.Known);
//        case "/friends":
////                var r = from().where("name", eq("Arthur"))
//            return getString(session.user.Friends);
//        case "/add":
//            return processCommandAddFriend(argument, session);
//        case "/play":
//            session.playing = true;
//            String firstQuestion = quizBot.getQuestionToOffer(session.user);
//            session.lastOfferedQuestion = firstQuestion;
//            return quizBot.getInstruction() + "\n" + firstQuestion;
//        case "/me":
//            return session.user.Login;
//        case "/stop":
//            session.playing = false;
//            return "Ok";
//        case "/exit":
//            session.user = null;
//            return "bye";
//        case "/save":
//            userManager.saveChanges();
//        return "done";
}
