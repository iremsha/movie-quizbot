public interface IQuizBot {
    String analyzeUserAnswer(String question, String userAnswer, Player player);
    String getQuestionToOffer(Player player);
    Player createUser();
}

