package Test;

import Bot.Bot;
import Bot.QuizBot;
import Bot.Session;
import Data.MoviesGetter;
import User.UserManager;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

class JThread extends Thread {
    private String msgForThread;
    static HashMap<Integer, Session> sessionsInThread = new HashMap<>();
    private HashMap<String, String> data = new MoviesGetter().getData();

    private  QuizBot quizBot = new QuizBot(data);
    private UserManager userManager = UserManager.getInstance();
    private  Bot bot = Bot.getInstance(quizBot, userManager);

    JThread(String name, String msg) throws IOException {
        super(name);
        this.msgForThread = msg;

    }

    public void run(){

        try{
            Thread.sleep(500);
            createAccount();

        }
        catch(InterruptedException | IOException e){
            System.out.println("Thread has been interrupted");
        }
    }

    private void createAccount() throws IOException {
        int a = 1; //Не очень красивые гранцы для рандома
        int b = 100000;

        String input_msg = msgForThread;

        int chat_id = a + (int) (Math.random() * b); //Рандом
        //List<String> output_msg = null;


        bot.processInput(input_msg, chat_id);
        //System.out.println(answerOne);

        bot.processInput(Thread.currentThread().getName() + " myPassword", chat_id);
        //System.out.println(answerTwo);

        Session session = bot.sessions.get(chat_id);
        var user = session.user;
        if (user != null){
            sessionsInThread.putIfAbsent(chat_id, session);
        }

        //System.out.println(user);
        //System.out.println(session);
        //System.out.println(Thread.currentThread().getName());


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

        assertEquals("Enter login and password separeted by space", output_msg.get(0));

    }

    @Test
    public void moreUserWithSameLogin() throws IOException {
        for (int i = 1; i < 10; i++) {
            new JThread("JThread", "/create").start();
        }

        assertEquals(1, JThread.sessionsInThread.size()); //Корретная ситуация когда создаласть только 1 уч.зп.

    }

    @Test
    public void moreUserWithDifferentLogin() throws IOException, InterruptedException {
        int n = 10;
        var listThread = new ArrayList<Thread>();
        for (int i = 0; i < n; i++) {
            listThread.add(new JThread("JThread"+i, "/create "));
        }

        for (int i = 0; i < n; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }
        assertEquals(n, JThread.sessionsInThread.size()); //Корретная ситуация когда создаласть только 1 уч.зп.
        JThread.sessionsInThread.clear();

    }
}

     
     