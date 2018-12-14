package Bot;
import User.*;

public interface IQuizBot {
    String analyzeUserAnswer(String question, String userAnswer, User user);
    String getQuestionToOffer(User user);
    String getInstruction();
}

