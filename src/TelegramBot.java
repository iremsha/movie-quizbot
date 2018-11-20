import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

//    private HashMap<Long, User> hash_chat_id = new HashMap<>();

    private HashMap<String, String> data = new HashMap<>() {{
        put("Pulp Fiction", "Tarantino");
        put("A Clockwork Orange", "Kubrick");
        put("Blue Velvet", "Lynch");
    }};

    private HashMap<String, String> dataArt = new HashMap<>() {{
        put("Pulp Fiction", "https://cms-assets.theasc.com/Pulp-Featured.jpg?mtime=20170115195049");
        put("A Clockwork Orange", "http://www.modernism-in-metroland.co.uk/uploads/1/0/2/5/10257505/28-days-thames_orig.jpg");
        put("Blue Velvet", "http://metrograph.com/uploads/films/8778124b4ac895f81682bbfbead14cd0-1515003257-726x388.jpg");
    }};



    private QuizBot quizBot = new QuizBot(data);
    private UserManager userManager = new UserManager();
    private Bot bot = new Bot(quizBot, userManager);

    public TelegramBot() throws IOException {
    }

//    private int sessionId = new Random().nextInt();


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String input_msg = update.getMessage().getText();
        Long chat_id = update.getMessage().getChatId();

        if (update.getMessage() != null && update.getMessage().hasText()) {
//            String output_msg = this.bot.processInput(input_msg, sessionId);
            ArrayList<String> output_msg = null;
            try {
                output_msg = this.bot.processInput(input_msg, chat_id.intValue());
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert output_msg != null;
            for (String message : output_msg) {
                sendMsg(chat_id, message);
            }

           if (output_msg.size() == 2) {
               sendPht(chat_id, dataArt.get(output_msg.get(1)));
           }

            //sendMsg(chat_id, nextBotMessage);
        }
    }

    private void sendMsg(long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);

        //setButtons(sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPht(long chatId, String p) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(p);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardHelpRow = new KeyboardRow();

        keyboardHelpRow.add(new KeyboardButton("/help"));

        KeyboardRow keyboardStartRow = new KeyboardRow();
        keyboardStartRow.add(new KeyboardButton("/start"));

        keyboard.add(keyboardHelpRow);
        keyboard.add(keyboardStartRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    @Override
    public String getBotUsername() {
        return "OOP_chat_bot";
    }

    @Override
    public String getBotToken() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("tgbot_token.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line: lines){
            return line;
        }
        return "";
    }
}





























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