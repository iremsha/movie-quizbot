package Bot;

import Commands.Command;
import Commands.CommandsCreator;
import User.IUserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bot implements IBot {

    public IQuizBot quizBot;
    public final String help = CommandsCreator.getHelp();

    public final String instruction = "If you've already played enter '/login' and your login\n" +
            "Else enter '/create' and your login\n" +
            "If you want some more information enter '/help\n";

    public IUserManager userManager;
    public ConcurrentHashMap<Integer, Session> sessions = new ConcurrentHashMap<>();
    public HashMap<String, Command> commands = CommandsCreator.getBotCommands();
    public static Bot instance;
    private Bot(IQuizBot quizBot, IUserManager userManager) {
        this.quizBot = quizBot;
        this.userManager = userManager;
    }

    public static Bot getInstance(IQuizBot quizBot, IUserManager userManager) {
        if (instance == null) {
            instance = new Bot(quizBot, userManager);
        }
        return instance;
    }


    public List<String> processInput(String userInput, int sessionId) throws IOException{
        sessions.putIfAbsent(sessionId, new Session());
        Session session = sessions.get(sessionId);

        String command = getCommand(userInput);
        String argument = getArgument(userInput);

        List<String> listMsg = new ArrayList<>();

        if(isPriorityCommand(command)){
            listMsg.add(processCommand(command,argument, session));
        }
        else if(session.user == null){
            listMsg.add(identifyUser(command, argument, session));
        }
        else if (isCommand(userInput)) { //is available command
            listMsg.add(processCommand(command, argument, session));
        }
        else if (session.action == UserAction.play) {
            String quizBotAnswer = quizBot.analyzeUserAnswer(session.lastOfferedQuestion, userInput, session.user);
            String nextQuestion = quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = nextQuestion;
            listMsg.add(quizBotAnswer);
            listMsg.add(nextQuestion);
        } else {
            listMsg.add(BotMessages.unexpectedInput);
        }
        return listMsg;
    }

    public String processCommand(String command, String argument, Session session) throws IOException{
        session.askForLoginAndPassword = false;
        return commands.get(command).Execute.commandFunction(this, argument, session);
    }


    public String identifyUser(String command, String argument, Session session) throws IOException {
        if (command.equals("login")||command.equals("create")){
            session.action = UserAction.valueOf(command);
            session.toButtonsCommands = new ArrayList<>();
            session.askForLoginAndPassword = true;
            return BotMessages.enterLoginAndPassword;
        }
        if (session.askForLoginAndPassword){
            String[] loginAndPassword = argument.split("\\s", 2);
            if (loginAndPassword.length != 2){
                return BotMessages.enterLoginAndPassword;
            }
            String login = loginAndPassword[0];
            String password = loginAndPassword[1];
            if (session.action == UserAction.login){
                if (!userManager.isUserInDB(login)){
                    return BotMessages.noUserWithThisLogin;
                }
                if (!userManager.isCorrectPassword(login, password)){
                    return BotMessages.incorrectPassword;
                }
                session.askForLoginAndPassword = false;
                session.action = null;
                session.user = userManager.getUser(login);
                return BotMessages.youLogInAs + " " + session.user.Login;
            }
            if(session.action == UserAction.create){
                //lock ??
                Lock lock = new ReentrantLock();
                lock.lock();
                if (userManager.isUserInDB(login)){
                    return BotMessages.loginHasTaken;
                }
                session.askForLoginAndPassword = false;
                session.action = null;
                session.user = userManager.createUser(login, password);
                lock.unlock();
                return BotMessages.youLogInAs + " " + session.user.Login;

                //unlock
            }
        }
        return BotMessages.needToLogin;
    }

    boolean isCommand(String userInput) {
        return commands.containsKey(getCommand(userInput));
//        return userInput.startsWith("/");
    }

    boolean isPriorityCommand(String command){
        return Arrays.asList("start", "help").contains(command);
    }

    private String getCommand(String input) {
        if (input.startsWith("/")) {
//            return input.split("\\s", 2)[0];
            return input.split("\\s", 2)[0].substring(1);
        }
        return "";
    }

    private String getArgument(String input) {
        if (input.startsWith("/")) {
            String[] arrInput = input.split("\\s", 2);
            return (arrInput.length < 2) ? "" : arrInput[1];
        }
        return input;
    }
}
