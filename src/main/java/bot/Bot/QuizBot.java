package bot.Bot;

import bot.User.User;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class QuizBot implements IQuizBot {

    private HashMap<String, String> questionAndAnswer;

    private String goodJobMessage = "Good job";
    private String badJobMessage = "Bad job";


    public QuizBot(HashMap<String, String> data) {
        questionAndAnswer = data;
    }


    public String analyzeUserAnswer(String question, String userAnswer, User user) {

        String correctAnswer = questionAndAnswer.get(question);
        userAnswer = userAnswer.trim();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            praiseUser(user, question);
            return goodJobMessage;
        }
        var correctAnswerArr = correctAnswer.split(" ");
        if (correctAnswerArr.length > 1 && userAnswer.equalsIgnoreCase(correctAnswerArr[1])) {
            praiseUser(user, question);
            return goodJobMessage;
        }

        noticeUserFail(user, question);
        return badJobMessage + ". Right answer is " + correctAnswer;
    }

    public String getQuestionToOffer(User user) {
        var questions = questionAndAnswer.keySet().toArray();
        int i = 0;
        while (i < questions.length && user.Known.contains(questions[i])) {
            i++;
        }
        if (i == questions.length) return "You know all our movies";
        return questions[i].toString();
    }

    private void praiseUser(User user, String question) {
        addKnowQuestion(user, question);
    }

    Set<String> addKnowQuestion(User user, String question){
        user.Known.add(question);
        return user.Known;
    }

    private void noticeUserFail(User user, String question) {
        user.Known.remove(question);
    }


    private String getRandomQuestion() {
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(questionAndAnswer.size());
        return questionAndAnswer.keySet().toArray()[rndNumber].toString();
    }


    public String getInstruction() {
        return "Let's play!\n" +
                "I will send you the name of movie and you'll name the director.\n";
    }

}