package bot.Commands;

import bot.Bot.*;
import bot.User.UserInfo;
import bot.User.UserInfoGetter;

import java.io.IOException;
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

    public static HashMap<String, Command> getBotCommands() {
        var commandsList = getListOfBotCommands();
        HashMap<String, Command> result = new HashMap<>();
        for (Command command : commandsList) {
            result.put(command.Name, command);
        }
        return result;
    }

    public static String getHelp(List<Command> commands) {
        StringBuilder help = new StringBuilder();
        for (var command : commands) {
            help.append(command.Name + "--" + command.Description + "\n");
        }
        return help.toString();
    }

    public static String getHelp() {
        return getHelp(getListOfBotCommands());
    }

    public static String getUserInfoCommand(UserInfo info, Bot bot, String login, int sessionId) throws IOException {
        var session = bot.sessions.get(sessionId);
        if (login.equals("") || login.equals(session.getUser().getLogin())) {
            return UserInfoGetter.get(info, session.getUser());
        }
        if (!bot.userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        if (!bot.userManager.hasUserPermission(session.getUser().getLogin(), login)) {
            return BotMessages.canSeeOnlyFriendsInfo;
        }
        return UserInfoGetter.get(info, bot.userManager.getUser(login));
    }
}