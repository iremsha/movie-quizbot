import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class QuizBot {


    private HashMap<String, String> questionAndAnswer;
    private Player player;

    private String goodJobMessage = "Good job";
    private String badJobMessage = "Bad job";


    public QuizBot(HashMap<String, String> data, Player player){
        questionAndAnswer = data;
        this.player = player;
    }

    public String analyzeUserAnswer(String userAnswer, Player player){

        String question = player.lastOfferedQuestion;
        String correctAnswer = questionAndAnswer.get(question);

        if (userAnswer.equalsIgnoreCase(correctAnswer)){
            praiseUser(player, 1, question);
            return goodJobMessage;
        }
         noticeUserFail(player, question);
        return badJobMessage;
    }

    public String getQuestionToOffer(Player player){
        String question = getRandomQuestion();
        while (player.Known.contains((question))){
            question = getRandomQuestion();
        }
        return question;
    }

    private void praiseUser(Player player, int score, String guestion){
        player.score += score;
        player.Known.add(guestion);
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
