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

    public Bot(IQuizBot quizBot, IUserManager userManager) {
        this.quizBot = quizBot;
        this.userManager = userManager;

//        sessions.computeIfPresent()
    }

    public List<String> processInput(String userInput, int sessionId) throws IOException{
        sessions.putIfAbsent(sessionId, new Session());
        Session session = sessions.get(sessionId);
//синхр доступ если с неск устрр (для тг)
        String command = getCommand(userInput);
        String argument = getArgument(userInput);

        List<String> listMsg = new ArrayList<>();
        if(isPriorityCommand(command)){
            listMsg.add(processCommand(command,argument, sessionId));
        }
        else if(session.getUser() == null || session.getAction() == UserAction.create || session.getAction() == UserAction.login ){
            listMsg.add(identifyUser(command, argument, sessionId));
        }
        else if (isCommand(userInput)) {
            listMsg.add(processCommand(command, argument, sessionId));
        }
        else if (session.getAction() == UserAction.play) {
            String quizBotAnswer = quizBot.analyzeUserAnswer(session.getLastOfferedQuestion(), userInput, session.getUser());
            String nextQuestion = quizBot.getQuestionToOffer(session.getUser());
            String lastOfferedQuestion = nextQuestion;
            sessions.put(sessionId, new Session(session.getUser(), session.getAction(),
                    lastOfferedQuestion, session.getAskForLoginAndPassword(), session.getToButtonsCommands()));
            listMsg.add(quizBotAnswer);
            listMsg.add(nextQuestion);
        } else {
            listMsg.add(BotMessages.unexpectedInput);
        }
        return listMsg;
    }

    public String processCommand(String command, String argument, int sessionId) throws IOException{
        var session = sessions.get(sessionId);
        sessions.put(sessionId, new Session(session.getUser(),
                session.getAction(), session.getLastOfferedQuestion(),
                false, session.getToButtonsCommands()));
        return commands.get(command).Execute.commandFunction(this, argument, sessionId);
    }

    //public Session changeSession(Session )

    public String identifyUser(String command, String argument, int sessionId) throws IOException {
        Session session = sessions.get(sessionId);
        if (session.getUser() == null){
            sessions.put(sessionId, new Session(null, null, null, false, ButtonsLists.whenUserNull()));

        }
        if (command.equals("login")||command.equals("create")){
            var action = UserAction.valueOf(command);
            sessions.put(sessionId, new Session(null, action, null, true, ButtonsLists.whenWaitForPassword()));
            return BotMessages.enterLoginAndPassword;
        }
        if (session.getAskForLoginAndPassword()){
            String[] loginAndPassword = argument.split("\\s", 2);
            if (loginAndPassword.length != 2){
                return BotMessages.enterLoginAndPassword;
            }
            String login = loginAndPassword[0];
            String password = loginAndPassword[1];
            if (session.getAction() == UserAction.login){
                return tryLoginUser(login, password, sessionId);
            }
            if(session.getAction() == UserAction.create){
                return tryCreateUser(login, password, sessionId);
                }
        }
        return BotMessages.needToLogin;
    }

    private String tryCreateUser(String login, String password, int sessionId) throws IOException {
        var session = sessions.get(sessionId);
        Lock lock = new ReentrantLock();
        lock.lock();
        if (userManager.isUserInDB(login)){
            return BotMessages.loginHasTaken;
        }
        var user = userManager.createUser(login, password);
        lock.unlock();
        commands.get("save").Execute.commandFunction(this, "", sessionId);
        session = new Session(user, null, null, false, ButtonsLists.whenNoPlaying());
        sessions.put(sessionId, session);
        return BotMessages.youLogInAs + " " + session.getUser().Login;
    }

    private String tryLoginUser(String login, String password, int sessionId){
        if (!userManager.isUserInDB(login)){
            return BotMessages.noUserWithThisLogin;
        }
        if (!userManager.isCorrectPassword(login, password)){
            return BotMessages.incorrectPassword;
        }
//        sessions.computeIfPresent(sessionId, null, null);
        var user = userManager.getUser(login);
        sessions.put(sessionId, new Session(user, null, null, false, ButtonsLists.whenNoPlaying()));

        return BotMessages.youLogInAs + " " + sessions.get(sessionId).getUser().Login;
    }


    boolean isCommand(String userInput) {
        return commands.containsKey(getCommand(userInput));
    }

    boolean isPriorityCommand(String command){
        return Arrays.asList("start", "help", "login", "create").contains(command);
    }

    private String getCommand(String input) {
        if (input.startsWith("/")) {
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
