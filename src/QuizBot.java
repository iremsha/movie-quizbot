import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

class QuizBot {


    private HashMap<String, String> questionAndAnswer;
    private Player player;

    private String goodJobMessage = "Good job";
    private String badJobMessage = "Bad job";


    public QuizBot(HashMap<String, String> data, Player player){
        questionAndAnswer = data;
        this.player = player;
    }

    String analyzeUserAnswer(String question, String userAnswer, Player player){

//        String question = player.lastOfferedQuestion;
        String correctAnswer = questionAndAnswer.get(question);

        if (userAnswer.equalsIgnoreCase(correctAnswer)){
            praiseUser(player, question);
            return goodJobMessage;
        }
        else if(userAnswer.startsWith("\\")) //Привет, я костыль
            return ".";

        noticeUserFail(player, question);
        return badJobMessage;
    }

    String getQuestionToOffer(Player player){
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
