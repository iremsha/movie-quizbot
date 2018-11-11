import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

class QuizBot implements IQuizBot {

    private HashMap<String, String> questionAndAnswer;

    private String goodJobMessage = "Good job";
    private String badJobMessage = "Bad job";



    public QuizBot(HashMap<String, String> data){
        questionAndAnswer = data;
    }


    public String analyzeUserAnswer(String question, String userAnswer, User user){

        String correctAnswer = questionAndAnswer.get(question);

        if (userAnswer.equalsIgnoreCase(correctAnswer)){
            praiseUser(user, question);
            return goodJobMessage;
        }

        noticeUserFail(user, question);
        return badJobMessage;
    }

    public String getQuestionToOffer(User user){
        if (user.Known.size() == questionAndAnswer.size())
            return "You know all our movies";
        String question = getRandomQuestion();
        while (user.Known.contains((question))){
            question = getRandomQuestion();
        }
        return question;
    }

    private void praiseUser(User user, String question){
        addKnowQuestion(user, question);
    }

    HashSet<String> addKnowQuestion(User user, String question){
        user.Known.add(question);
        return user.Known;
    }

    private void noticeUserFail(User user, String question){
        user.Known.remove(question);
    }


    private String getRandomQuestion(){
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(questionAndAnswer.size());
        return questionAndAnswer.keySet().toArray()[rndNumber].toString();
    }

    public String getInstruction() {
        return "Let's play!\n" +
                "I will send you the name of movie and you'll name the director.\n";
    }

}
