//package Game;
//
//import Bot.Bot;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TelegramBot extends TelegramLongPollingBot {
//
//    private Bot bot;
//
//    public TelegramBot(Bot bot){
//        this.bot = bot;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        String input_msg = update.getMessage().getText();
//        Long chat_id = update.getMessage().getChatId();
//
//        if (update.getMessage() != null && update.getMessage().hasText()) {
////            String output_msg = this.bot.processInput(input_msg, sessionId);
//            List<String> output_msg = null;
//            try {
//                output_msg = this.bot.processInput(input_msg, chat_id.intValue());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            assert output_msg != null;
//            for (String message : output_msg) {
//                sendMsg(chat_id, message);
//            }
//
//
//            //sendMsg(chat_id, nextBotMessage);
//        }
//    }
//
//    private synchronized void sendMsg(long chatId, String s) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(s);
//
//        setButtons(sendMessage);
//
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setButtons(SendMessage sendMessage) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
//        var chatId = Integer.valueOf(sendMessage.getChatId());
//        KeyboardRow keyboardOneRow = new KeyboardRow();
//        for (String textButton : bot.sessions.get(chatId).getToButtonsCommands()) {
//            KeyboardButton button = new KeyboardButton("/" + textButton);
//            keyboardOneRow.add(button);
//        }
//        keyboard.add(keyboardOneRow);
//
//        /*KeyboardRow keyboardOneRow = new KeyboardRow(); --Кнопки в строчку
//        for (String textButtons : bot.createButtons(sendMessage.getText())){
//            keyboardOneRow.add(new KeyboardButton(textButtons));
//        }
//        keyboard.add(keyboardOneRow);*/
//
//        replyKeyboardMarkup.setKeyboard(keyboard);
//    }
//    @Override
//    public String getBotUsername() {
//        return "OOP_chat_bot";
//    }
//
//    @Override
//    public String getBotToken() {
//        List<String> lines = null;
//        try {
//            lines = Files.readAllLines(Paths.get("tgbot_token.txt"), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(String line: lines){
//            return line;
//        }
//        return "";
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////import org.telegram.telegrambots.bots.TelegramLongPollingBot;
////import org.telegram.telegrambots.api.objects.Update;
////
////public class Game.TelegramBot extends TelegramLongPollingBot{
////    public static void main(String[] args) {
////        ApiContextInitializer.init(); // Инициализируем апи
////        TelegramBotsApi botapi = new TelegramBotsApi();
////        try {
////            botapi.registerBot(new Bot.Bot());
////        } catch (TelegramApiException e) {
////            e.printStackTrace();
////        }
////    }
////    @Override
////    public String getBotUsername() {
////        return "USER";
////        //возвращаем юзера
////    }
////
////    @Override
////    public void onUpdateReceived(Update e) {
////        // Тут будет то, что выполняется при получении сообщения
////    }
////
////    @Override
////    public String getBotToken() {
////        return "YOUR_BOT_TOKEN";
////        //Токен бота
////    }
////
////}