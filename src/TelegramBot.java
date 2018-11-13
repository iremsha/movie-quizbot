//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.api.objects.Update;
//
//public class TelegramBot extends TelegramLongPollingBot{
//    public static void main(String[] args) {
//        ApiContextInitializer.init(); // Инициализируем апи
//        TelegramBotsApi botapi = new TelegramBotsApi();
//        try {
//            botapi.registerBot(new Bot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public String getBotUsername() {
//        return "USER";
//        //возвращаем юзера
//    }
//
//    @Override
//    public void onUpdateReceived(Update e) {
//        // Тут будет то, что выполняется при получении сообщения
//    }
//
//    @Override
//    public String getBotToken() {
//        return "YOUR_BOT_TOKEN";
//        //Токен бота
//    }
//
//}