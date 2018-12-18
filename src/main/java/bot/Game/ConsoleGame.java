package bot.Game;

import bot.Bot.*;
import bot.Data.*;
import bot.User.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
    private HashMap<String, String> data = new MoviesGetter("https://www.kinopoisk.ru/user/13956769/movies/list/type/363730/#list").getData();

    ConsoleGame() throws IOException {
    }

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        ConsoleGame game = new ConsoleGame();
        String link = "https://www.kinopoisk.ru/user/13956769/movies/list/type/363730/#list";
        var data = new MoviesGetter(link).getData();
        QuizBot quizBot = new QuizBot(data);
        UserManager userManager = UserManager.getInstance();
        Bot bot = new Bot(quizBot, userManager);
        Scanner scan = new Scanner(System.in);
        int sessionId = new Random().nextInt();

        while (true) {
            String userInput = scan.nextLine();
            List<String> botAnswer = bot.processInput(userInput, sessionId);
            System.out.println(botAnswer);
        }

    }
}
