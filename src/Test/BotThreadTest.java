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
    public static HashMap<Integer, Session> sessionsInThread = new HashMap<>();
    private HashMap<String, String> data = new MoviesGetter().getData();
    JThread(String name) throws IOException {
        super(name);
    }

    public void run(){

        try{

            int a = 1; //Не очень красивые гранцы для рандома

            int b = 100000;
            Thread.sleep(100);
            QuizBot quizBot = new QuizBot(data);
            UserManager userManager = UserManager.getInstance();
            Bot bot = Bot.getInstance(quizBot, userManager);

            String input_msg = "/create " + Thread.currentThread().getName();
            //Long chat_id = 1L;
            int chat_id = a + (int) (Math.random() * b); //Рандом
            List<String> output_msg = null;


            var otv1 = bot.processInput(input_msg, chat_id);
            System.out.println(otv1);

            var otv2 = bot.processInput("pass", chat_id);
            System.out.println(otv2);




            Session session = bot.sessions.get(chat_id);


            var user = session.user;
            if (user != null){
            sessionsInThread.putIfAbsent(chat_id, session);
            }

            System.out.println(user);
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
    public void isThread() throws IOException {
        for (int i = 1; i < 10; i++)
            new JThread("JThread " + i).start();

        var t = new JThread("JThread ");
        System.out.println(t.sessionsInThread.size());

    }
}

     
     