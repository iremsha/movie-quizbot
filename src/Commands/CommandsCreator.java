package Commands;

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
}
