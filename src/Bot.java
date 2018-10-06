import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;


class Bot {

    private String instruction;
    private Player player;
    private QuizBot quizBot;

    String lastMessage;



    public Bot(String instruction, Player player, QuizBot quizBot){
        this.instruction = instruction;
        this.player = player;
        this.quizBot = quizBot;
    }

    public String getStartMessage(){
        return instruction;
    }

    public String getNextMessage(){
        player.lastOfferedQuestion = quizBot.getQuestionToOffer(player);
        return player.lastOfferedQuestion;
    }

    public String processInput(String userInput, Player currentPlayer){
        if (isCommand(userInput))
            return processCommand(userInput, currentPlayer);
        return quizBot.analyzeUserAnswer(userInput, player);
    }


    private boolean isCommand(String userInput){
//        return COMMANDS.containsKey(userInput);
        return userInput.startsWith("\\");
    }

    private String processCommand(String command, Player currentPlayer){
        switch(command) {
            case "\\start" :
                return instruction;

            case "\\help" :
                return instruction;

            case "\\score" :
                return "555";//toString(currentPlayer.score);

            case "\\stop" :
                currentPlayer.wantsToPlay = false;
                break;

            default :
                return "Incorrect command";
        }
        return "";
    }
}
