package Game;

import Bot.*;
import User.*;
import Data.*;

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

    private HashMap<String, String> data = new MoviesGetter().getData();

    private QuizBot quizBot = new QuizBot(data);
    private UserManager userManager = UserManager.getInstance();
    private Bot bot = Bot.getInstance(quizBot, userManager);

    public TelegramBot() throws IOException {
    }

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
            List<String> output_msg = null;
            try {
                output_msg = this.bot.processInput(input_msg, chat_id.intValue());
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert output_msg != null;
            for (String message : output_msg) {
                sendMsg(chat_id, message);
            }


            //sendMsg(chat_id, nextBotMessage);
        }
    }

    private synchronized void sendMsg(long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);

        setButtons(sendMessage);

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

    private void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

         for (String textButtons : bot.createButtons(sendMessage.getText())){
            KeyboardRow keyboardOneRow = new KeyboardRow();
            keyboardOneRow.add(new KeyboardButton(textButtons));
            keyboard.add(keyboardOneRow);

        }

        /*KeyboardRow keyboardOneRow = new KeyboardRow(); --Кнопки в строчку
        for (String textButtons : bot.createButtons(sendMessage.getText())){
            keyboardOneRow.add(new KeyboardButton(textButtons));
        }
        keyboard.add(keyboardOneRow);*/

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
//public class Game.TelegramBot extends TelegramLongPollingBot{
//    public static void main(String[] args) {
//        ApiContextInitializer.init(); // Инициализируем апи
//        TelegramBotsApi botapi = new TelegramBotsApi();
//        try {
//            botapi.registerBot(new Bot.Bot());
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