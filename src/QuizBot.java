import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

class QuizBot implements IQuizBot {


    private HashMap<String, String> questionAndAnswer;
    private Player player;

    private String goodJobMessage = "Good job";
    private String badJobMessage = "Bad job";


    public QuizBot(HashMap<String, String> data, Player player){
        questionAndAnswer = data;
        this.player = player;
    }

    public String analyzeUserAnswer(String question, String userAnswer, Player player){

        String correctAnswer = questionAndAnswer.get(question);

        if (userAnswer.equalsIgnoreCase(correctAnswer)){
            praiseUser(player, question);
            return goodJobMessage;
        }

        noticeUserFail(player, question);
        return badJobMessage;
    }

    public String getQuestionToOffer(Player player){
        if (!player.wantsToPlay || player.Known.size() == questionAndAnswer.size())
            return "";
        String question = getRandomQuestion();
        while (player.Known.contains((question))){
            question = getRandomQuestion();
        }
        return question;
    }

    private void praiseUser(Player player, String question){
        addScore(player);
        addKnowQuestion(player, question);
    }

    int addScore(Player player){
        player.score += 1;
        return player.score;
    }

    HashSet<String> addKnowQuestion(Player player, String qustion){
        player.Known.add(qustion);
        return player.Known;
    }

    private void noticeUserFail(Player player, String question){
        player.Failures.add(question);
    }


    private String getRandomQuestion(){
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(questionAndAnswer.size());
        return questionAndAnswer.keySet().toArray()[rndNumber].toString();
    }

}
