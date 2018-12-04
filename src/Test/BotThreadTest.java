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
import static org.testng.AssertJUnit.assertTrue;

class JThread extends Thread {
    private String msgForThread;
    private final String truePassword = " truePassword";
    private final String falsePassword = " falsePassword";


    static HashMap<Integer, Session> sessionsInThread = new HashMap<>();
    public static ArrayList<String> answerForThreadFromBot = new ArrayList<>();
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
            Thread.sleep(0);
            int a = 1;
            int b = 100000;
            int chat_id = a + (int) (Math.random() * b);

            switch (msgForThread){

                case "/create": createAccount(msgForThread, chat_id); break;
                case "/login": loginAccount(msgForThread, chat_id); break;
            }


        }
        catch(InterruptedException | IOException e){
            System.out.println("Thread has been interrupted");
        }
    }

    private void createAccount(String input_msg, int chat_id) throws IOException {
        //List<String> output_msg = null;

        bot.processInput(input_msg, chat_id);

        bot.processInput(Thread.currentThread().getName() + truePassword, chat_id);
        //System.out.println(answerTwo);

        Session session = bot.sessions.get(chat_id);
        var user = session.user;
        if (user != null){
            sessionsInThread.putIfAbsent(chat_id, session);
        }
    }

    private void loginAccount(String input_msg, int chat_id) throws IOException {
        createAccount("/create", chat_id);

        bot.processInput("/exit", chat_id);
        bot.processInput(input_msg, chat_id);
        String answer = bot.processInput(Thread.currentThread().getName() + truePassword, chat_id).get(0);
        //System.out.println(answer);

        answerForThreadFromBot.add(answer);
        //System.out.println(bot.processInput(Thread.currentThread().getName() + truePassword, chat_id).get(0));
        //System.out.println(answerForThreadFromBot.size());
    }
}

public class BotThreadTest {

    private HashMap<String, String> data = new MoviesGetter().getData();
    private final int numberOfThreads = 100;

    private QuizBot quizBot = new QuizBot(data);
    private UserManager userManager = UserManager.getInstance();
    private Bot bot = Bot.getInstance(quizBot, userManager);

    public BotThreadTest() throws IOException {
    }

    @Test
    public void isCommandTestFalse() throws IOException {
        String input_msg = "/create";
        Long chat_id = 1L;

        List<String> output_msg = null;
        output_msg = this.bot.processInput(input_msg, chat_id.intValue());

        assertEquals("Enter login and password separeted by space", output_msg.get(0));

    }

    @Test
    public void moreUserCreateWithSameLogin() throws IOException, InterruptedException {
        var listThread = new ArrayList<JThread>();
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("JThread", "/create"));
        }

        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }
        assertEquals(1, JThread.sessionsInThread.size()); //Корретная ситуация когда создаласть только 1 уч.зп.
    }
    @Test
    public void moreUserCreateWithDifferentLogin() throws IOException, InterruptedException {
        var listThread = new ArrayList<JThread>();
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("JThread"+i, "/create"));
        }

        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }
        assertEquals(numberOfThreads, JThread.sessionsInThread.size()); //Корретная ситуация когда = кол-ву потоков.
        JThread.sessionsInThread.clear();
    }

    @Test
    public void moreUserGoWithSameLogin() throws IOException, InterruptedException {
        var result = true;
        var listThread = new ArrayList<JThread>();
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("JThread", "/login"));
        }
        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }

        for (int i = 0; i < numberOfThreads; i++){
            if (JThread.answerForThreadFromBot.get(i).equals("You log in as JThread")){
                result = false;
            }
        }
        assertTrue(result); //Корретная ситуация когда все защли под своим логином.

    }

    @Test
    public void moreUserGoWithDifferent3ntLogin() throws IOException, InterruptedException {
        var result = true;
        var listThread = new ArrayList<JThread>();
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("JThread"+i, "/login"));
        }

        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }

        for (int i = 0; i < numberOfThreads; i++){
            if (JThread.answerForThreadFromBot.get(i).equals("You log in as "+listThread.get(0).getName())){
                result = false;
            }

        }
        assertTrue(result); //Корретная ситуация когда все защли под своим логином.
    }
}

     
     