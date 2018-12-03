package Commands;

import Bot.*;
import User.UserInfo;
import User.UserInfoGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandsCreator {
    public static List<Command> getListOfBotCommands() {
        List<Command> commands = new ArrayList<>();

        commands.add(StartCommandCreator.create());
        commands.add(HelpCommandCreator.create());
        commands.add(CreateCommandCreator.create());
        commands.add(LoginCommandCreator.create());
        commands.add(ScoreCommandCreator.create());
        commands.add(MoviesCommandCreator.create());
        commands.add(FriendsCommandCreator.create());
        commands.add(AddCommandCreator.create());
        commands.add(PlayCommandCreator.create());
        commands.add(StopCommandCreator.create());
        commands.add(MeCommandCreator.create());
        commands.add(ExitCommandCreator.create());
        commands.add(SaveCommandCreator.create());
//            case "/start":
//        case "/help":
//        case "/create":
//        case "/login":
//        case "/score":
//        case "/movies":
//        case "/friends":
//        case "/add":
//        case "/play":
//        case "/me":
//        case "/stop":
//        case "/exit":
//        case "/save":
        return commands;
    }
    public static HashMap<String, Command> getBotCommands(){
        var commandsList = getListOfBotCommands();
        HashMap<String, Command> result = new HashMap<>();
        for(Command command:commandsList){
            result.put(command.Name, command);
        }
        return result;
    }
    public static String getHelp(List<Command> commands){
        StringBuilder help = new StringBuilder();
        for (var command:commands){
            help.append(command.Name + "--" + command.Description + "\n");
        }
        return help.toString();
    }
    public static String getHelp(){
        return getHelp(getListOfBotCommands());
    }

    public static String getUserInfoCommand(UserInfo info, Bot bot, String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)) {
            return UserInfoGetter.get(info, session.user);
        }
        if (!bot.userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        if (!bot.userManager.hasUserPermission(session.user.Login, login)) {
            return "You can see only your friends' information";
        }
        return UserInfoGetter.get(info, bot.userManager.getUser(login));
    }
}
