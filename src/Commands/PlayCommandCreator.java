package Commands;

import Bot.UserAction;

import java.util.Arrays;

public class PlayCommandCreator {
    public static Command create(){
        String name = "play";
        String description = "play quiz";
        CommandFunction commandFunction = (bot, login, session) -> {
            session.action = UserAction.play;
            String firstQuestion = bot.quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = firstQuestion;
            session.toButtonsCommands = Arrays.asList("stop", "score");
            return bot.quizBot.getInstruction() + "\n" + firstQuestion;
        };
        return  new Command(name, description, commandFunction);
    }
}
