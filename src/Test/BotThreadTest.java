package Test;
import Bot.Bot;
import Bot.QuizBot;
import Bot.Session;
import Data.MoviesGetter;
import User.UserManager;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

class JThread extends Thread {
    private HashMap<String, String> data = new MoviesGetter().getData();
    JThread(String name) throws IOException {
        super(name);
    }

    public void run(){

        try{

            int a = 1;
            int b = 10;
            Thread.sleep(500);
            QuizBot quizBot = new QuizBot(data);
            UserManager userManager = UserManager.getInstance();
            Bot bot = Bot.getInstance(quizBot, userManager);

            String input_msg = "/create "+ Thread.currentThread().getName(); //Текст сообщения!
            Long chat_id = 1L;

            List<String> output_msg = null;


            bot.processInput(input_msg, a + (int) (Math.random() * b));


            Session session = bot.sessions.get(chat_id.intValue());
            System.out.println(session);
            System.out.println(Thread.currentThread().getName());

        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


public class BotThreadTest {
    private HashMap<String, String> data = new MoviesGetter().getData();

    private String instruction = "If you've already played enter '/login' and your login\n" +
            "Else enter '/create' and your login\n" +
            "If you want some more information enter '/help\n";

    public HashMap<Integer, Session> sessions = new HashMap<>();  //why can't int
    public static Bot instance;

    private QuizBot quizBot = new QuizBot(data);
    private UserManager userManager = UserManager.getInstance();
    private Bot bot = Bot.getInstance(quizBot, userManager);

    public BotThreadTest() throws IOException {
    }

    @Test
    public void isCommandTestFalse() throws IOException {
        String input_msg = "/create Remsha"; //Текст сообщения!
        Long chat_id = 1L;

        List<String> output_msg = null;
        output_msg = this.bot.processInput(input_msg, chat_id.intValue());

        assertEquals("Enter password", output_msg.get(0));

    }

    @Test
    public void isPizdec() throws IOException{
        for(int i=1; i < 10; i++)
            new JThread("JThread " + i).start();
    }
}

     
     