import java.io.IOException;

public class Main {

    public static void main(String[] args){
        ConsoleGame game = new ConsoleGame();
        game.startGame();
    }
    public static void main1(String[] args) {
//        ApiContextInitializer.init();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot(Bot.getBot());
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }
    }
    public static void main0(String[] args) throws IOException {
        DataSaver.save("l", new User("n", "a"));
        System.out.println(DataSaver.getData("h"));

    }
}
