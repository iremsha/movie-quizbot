import java.util.Scanner;
import java.util.HashMap;


public class ConsoleGame {

    public void startGame(){

        Player player = createUser();
        HashMap<String, String> data = new HashMap<String, String>(){{
                put("Pulp Fiction", "Tarantino");
                put("A Clockwork Orange", "Kubrick");
                put("Blue Velvet", "Lynch");
            }};
        String botInstruction = "I'd like you to play the game!\n" +
                "I will send you the name of movie and you'll name the director." +
                "You've got 3 attemps for every movie\n";

        QuizBot quizBot = new QuizBot(data, player);
        Bot bot = new Bot(botInstruction, player, quizBot);
        Scanner scan = new Scanner(System.in);

        System.out.println(bot.getStartMessage());

        while (player.wantsToPlay){
            System.out.println("Next movie: " + bot.getNextMessage());
            String userAnswer = scan.nextLine();
            String botAnswer = bot.processInput(userAnswer, player);
            System.out.println(botAnswer);
        }
    }

    private Player createUser(){
        System.out.println("Enter your name");
        Scanner scan = new Scanner(System.in);
        String userName = scan.nextLine();
        System.out.println("Hello, " + userName);

        return new Player(userName);
    }


}
