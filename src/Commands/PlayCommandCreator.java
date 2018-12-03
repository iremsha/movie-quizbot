package Commands;

import Bot.UserAction;

public class PlayCommandCreator {
    public static Command create(){
        String name = "play";
        String description = "play quiz";
        CommandFunction commandFunction = (bot, login, session) -> {
            session.action = UserAction.play;
            String firstQuestion = bot.quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = firstQuestion;
            return bot.quizBot.getInstruction() + "\n" + firstQuestion;
        };
        return  new Command(name, description, commandFunction);
    }
}
