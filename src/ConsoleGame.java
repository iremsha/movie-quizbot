import java.util.HashMap;
import java.util.Scanner;


class ConsoleGame {
    private HashMap<String, String> data = new HashMap<>(){{
    put("Pulp Fiction", "Tarantino");
    put("A Clockwork Orange", "Kubrick");
    put("Blue Velvet", "Lynch");
}};

    void startGame(){
        String botInstruction = "I'd like you to play the game!\n" +
                "I will send you the name of movie and you'll name the director.\n";

        String botHelp = "\\start - start game \n" +
                "\\score - show current score \n" +
                "\\stop - stop game \n" +
                "\\help - help?";

        QuizBot quizBot = new QuizBot(data);
        Player player =  quizBot.createUser();
        Bot bot = new Bot(botInstruction, botHelp, player, quizBot);
        Scanner scan = new Scanner(System.in);

        System.out.println(bot.getStartMessage());

        while (true){
            String nextBotMessage = bot.getNextMessage();
            System.out.println(nextBotMessage );
            String userAnswer = scan.nextLine();
            String botAnswer = bot.processInput(nextBotMessage, userAnswer, player);
            System.out.println(botAnswer);
        }
     }

}
