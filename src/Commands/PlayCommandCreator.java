package Commands;

import Bot.Session;
import Bot.UserAction;

import java.util.Arrays;

public class PlayCommandCreator {
    public static Command create(){
        String name = "play";
        String description = "play quiz";
        CommandFunction commandFunction = (bot, login, sessionId) -> {
            var session = bot.sessions.get(sessionId);
            var action = UserAction.play;
            String firstQuestion = bot.quizBot.getQuestionToOffer(session.getUser());
            var lastOfferedQuestion = firstQuestion;
            var toButtonsCommands = Arrays.asList("stop", "score");
            bot.sessions.put(sessionId, new Session(session.getUser(), action, lastOfferedQuestion, session.getAskForLoginAndPassword(),
                    toButtonsCommands));
            return bot.quizBot.getInstruction() + "\n" + firstQuestion;
        };
        return  new Command(name, description, commandFunction);
    }
}
