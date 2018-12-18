package Test;

import Bot.Bot;
import Bot.Session;
import User.UserManagerForTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

class JThread extends Thread {
    private String msgForThread;
    private final String truePassword = " truePassword";


//    static HashMap<Integer, Session> sessionsInThread = new HashMap<>();
    public static ArrayList<String> answerForThreadFromBot = new ArrayList<>();

    private  Bot bot;

    JThread(String name, String msg, Bot bot) throws IOException {
        super(name);
        this.msgForThread = msg;
        this.bot = bot;
    }

    public void run(){

        try{
            Thread.sleep(0);
            int a = 1;
            int b = 1000000000;
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
        bot.processInput(input_msg, chat_id);

        bot.processInput(Thread.currentThread().getName() + truePassword, chat_id);
        //System.out.println(answerTwo);
        Session session = bot.sessions.get(chat_id);
        var user = session.getUser();
//        if (user != null){
//            sessionsInThread.putIfAbsent(chat_id, session);
//        }
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

    private final int numberOfThreads = 10000;

//    private UserManager userManager = UserManager.getInstance();
//    private UserManagerForTest userManager = UserManagerForTest.getInstance();
//    private Bot bot = new Bot(null, userManager);

    public BotThreadTest() throws IOException {

    }

//    @Test
//    public void isCommandTestFalse() throws IOException {
//        String input_msg = "/create";
//
//        Long chat_id = 1L;
//
//        List<String> output_msg = null;
//        Bot bot = new Bot(null, UserManagerForTest.getInstance());
//        output_msg = bot.processInput(input_msg, chat_id.intValue());
//
//        assertEquals("Enter login and password separeted by space", output_msg.get(0));
//
//    }

    @Test
    public void moreUserCreateWithSameLogin() throws IOException, InterruptedException {
        var listThread = new ArrayList<JThread>();
        Bot bot = new Bot(null, UserManagerForTest.getInstance());
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("SameName", "/create", bot));
        }

        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }
        List<Session> loggedInSessions = new ArrayList<>();
        for (var session : bot.sessions.values()){
            if (session.getUser() != null){
                loggedInSessions.add(session);
            }
        }
        //assertEquals(1, JThread.sessionsInThread.size()); //Корретная ситуация когда создаласть только 1 уч.зп.
        assertEquals(loggedInSessions.size(), 1); //Корретная ситуация когда создаласть только 1 уч.зп.
    }
    @Test
    public void moreUserCreateWithDifferentLogin() throws IOException, InterruptedException {
        var listThread = new ArrayList<JThread>();
        Bot bot = new Bot(null, UserManagerForTest.getInstance());
        for (int i = 0; i < numberOfThreads; i++) {
            listThread.add(new JThread("DifferentName"+i, "/create", bot));
        }

        for (int i = 0; i < numberOfThreads; i++){
            listThread.get(i).start();
            listThread.get(i).join();
        }
        List<Session> loggedInSessions = new ArrayList<>();
        for (var session : bot.sessions.values()){
            if (session.getUser() != null){
                loggedInSessions.add(session);
            }
        }
        assertEquals(loggedInSessions.size(), numberOfThreads); //Корретная ситуация когда = кол-ву потоков.
//        JThread.sessionsInThread.clear();
    }

//    @Test
//    public void moreUserGoWithSameLogin() throws IOException, InterruptedException {
//        var result = true;
//        var listThread = new ArrayList<JThread>();
//        Bot bot = new Bot(null, UserManagerForTest.getInstance());
//        for (int i = 0; i < numberOfThreads; i++) {
//            listThread.add(new JThread("JThread", "/login", bot));
//        }
//        for (int i = 0; i < numberOfThreads; i++){
//            listThread.get(i).start();
//            listThread.get(i).join();
//        }
//
//        for (int i = 0; i < numberOfThreads; i++){
//            if (JThread.answerForThreadFromBot.get(i).equals("You log in as JThread")){
//                result = false;
//            }
//        }
//        assertTrue(result); //Корретная ситуация когда все защли под своим логином.
//
//    }
//
//    @Test
//    public void moreUserGoWithDifferent3ntLogin() throws IOException, InterruptedException {
//        var result = true;
//        var listThread = new ArrayList<JThread>();
//        Bot bot = new Bot(null, UserManagerForTest.getInstance());
//        for (int i = 0; i < numberOfThreads; i++) {
//            listThread.add(new JThread("JThread"+i, "/login", bot));
//        }
//
//        for (int i = 0; i < numberOfThreads; i++){
//            listThread.get(i).start();
//            listThread.get(i).join();
//        }
//
//        for (int i = 0; i < numberOfThreads; i++){
//            if (JThread.answerForThreadFromBot.get(i).equals("You log in as "+listThread.get(0).getName())){
//                result = false;
//            }
//
//        }
//        assertTrue(result); //Корретная ситуация когда все защли под своим логином.
//    }
}

     
     