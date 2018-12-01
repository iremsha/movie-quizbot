package Game;

import Bot.*;
import Data.*;
import User.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


class ConsoleGame {
//    private HashMap<String, String> data = new HashMap<>(){{
//        put("Pulp Fiction", "Tarantino");
//        put("A Clockwork Orange", "Kubrick");
//        put("Blue Velvet", "Lynch");
//    }};
    private HashMap<String, String> data = new MoviesGetter().getData();

    ConsoleGame() throws IOException {
    }

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        ConsoleGame game = new ConsoleGame();
        QuizBot quizBot = new QuizBot(game.data);
        UserManager userManager = UserManager.getInstance();
        Bot bot = Bot.getInstance(quizBot, userManager);

        Scanner scan = new Scanner(System.in);
        int sessionId = new Random().nextInt();
        System.out.println(bot.processInput("/start", sessionId));
        while (true){
            String userInput = scan.nextLine();
            List<String> botAnswer = bot.processInput(userInput, sessionId);
            System.out.println(botAnswer);
        }
     }
}
