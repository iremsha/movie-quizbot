package Bot;

import Commands.Command;
import Commands.CommandsCreator;
import User.IUserManager;
import User.User;
import User.UserInfo;
import User.UserInfoGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Bot implements IBot {

    public IQuizBot quizBot;
    private final String help = "/start - start game \n" + //дописать команды
            "/score - show current score \n" +
            "/stop - stop game \n" +
            "/help - help?";
    //public Bot.BotMessages botMessages = new Bot.BotMessages();

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
        List<String> listMsg = new ArrayList<>();
        sessions.putIfAbsent(sessionId, new Session());
        Session session = sessions.get(sessionId);

        System.out.println(session);


        String command = getCommand(userInput);
        String argument = getArgument(userInput);

        if (isCommand(userInput)) { //is available command
            listMsg.add(processCommand(command, argument, session));
        } else if (session.askForPassword) {
            listMsg.add(tryIdentifyUser(argument, session));
        } else if (session.user == null) {
            listMsg.add(BotMessages.needLogin);
        } else if (session.playing) {
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
        session.askForLogin = false;
        session.askForPassword = false;
        return commands.get(command).Execute.commandFunction(this, argument, session);
//        switch (command) {
//            case "/start":
//                session.toButtonsCommands = new ArrayList<>(Arrays.asList("login", "create"));
//                return instruction;
//            case "/help":
//                return help;
//            case "/create":
//                session.toButtonsCommands.clear();
//                return processCommandCreate(argument, session);
//            case "/login":
//                session.toButtonsCommands.clear();
//                return processCommandLogin(argument, session);
//            case "/score":
//                return processCommandGetInfo(User.UserInfo.Score, argument, session);
//            case "/movies":
//                return processCommandGetInfo(User.UserInfo.Movies, argument, session);
//            case "/friends":
//                return processCommandGetInfo(User.UserInfo.Friends, argument, session);
//            case "/add":
//                return processCommandAddFriend(argument, session);
//            case "/play":
//                session.toButtonsCommands = new ArrayList<>(Arrays.asList("stop", "score"));
//                session.playing = true;
//                String firstQuestion = quizBot.getQuestionToOffer(session.user);
//                session.lastOfferedQuestion = firstQuestion;
//                return quizBot.getInstruction() + "\n" + firstQuestion;
//            case "/me":
//                return session.user.Login;
//            case "/stop":
//                session.playing = false;
//                return "Ok";
//            case "/exit":
//                session.user = null;
//                return "bye";
//            case "/save":
//                userManager.saveChanges();
//                return "done";
//            default:
//                return incorrectCommand;
//        }
    }

    private String processCommandGetInfo(UserInfo info, String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)) {
            return UserInfoGetter.get(info, session.user);
        }
        if (!userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        if (!hasUserPermission(session.user.Login, login)) {
            return "You can see only your friends' information";
        }
        return UserInfoGetter.get(info, userManager.getUser(login));
    }

    private String processCommandFriends(String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)) {
            return session.user.Friends.toString();
        }
        if (!userManager.isUserInDB(login)) {
            return "No user with this login";
        }
        if (!hasUserPermission(session.user.Login, login)) {
            return "You can see only your friends' information";
        }
        return userManager.getUser(login).Friends.toString();
    }

    private String processCommandMovies(String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)) {
            return session.user.Known.toString();
        }
        if (!userManager.isUserInDB(login)) {
            return "No user with this login";
        }
        if (!hasUserPermission(session.user.Login, login)) {
            return "You can see only your friends' information";
        }
        return userManager.getUser(login).Known.toString();
    }

    private String processCommandScore(String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)) {
            return String.valueOf(session.user.getScore());
        }
        if (!userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        if (!hasUserPermission(session.user.Login, login)) {
            return "You can see only your friends' information";
        }
        return String.valueOf(userManager.getUser(login).getScore());
    }

    private String processCommandCreate(String login, Session session) {
        if (login.isEmpty()) {
            return "Empty login";
        }
//        session.user = null;
        if (userManager.isUserInDB(login)) {
            return "This login has already taken";
        }
        session.enteredLogin = login;
        session.askForPassword = true;
        return "Enter password";
    }

    public String processCommandLogin(String login, Session session) {
        if (login.isEmpty()) {
            return BotMessages.emptyLogin;
        }
        session.user = null;
        if (!userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        session.enteredLogin = login;
        session.askForPassword = true;
        return BotMessages.enterPassword;
    }

    public String processCommandAddFriend(String argument, Session session) {
        String login = argument;
        if (login.equals("")) {
            return "Need login after command";
        }
        if (login.equals(session.user.Login)) {
            return "You can't add yourself";
        }
        if (!userManager.isUserInDB(login)) {
            return BotMessages.noUserWithThisLogin;
        }
        if (userManager.areFriends(session.user.Login, login)) {
            return "You've already added this user";
        }
        userManager.addFriendToUser(session.user.Login, login);
        return "Done";
    }

    private boolean hasUserPermission(String login, String anotherLogin) {
        User user = userManager.getUser(login);
        User anotherUser = userManager.getUser(anotherLogin);
        return hasUserPermission(user, anotherUser);
    }

    private boolean hasUserPermission(User user, User anotherUser) {
        return userManager.areFriends(user, anotherUser);
    }

    boolean isCommand(String userInput) {
//        return COMMANDS.containsKey(userInput);
        return userInput.startsWith("/");
    }

    private boolean isPriorityCommand(String input) {
        List<String> priorityCommands = Arrays.asList(new String[]{"/start", "/help", "/stop", "/create", "/login", "/save"});
        return priorityCommands.contains(input); //проверить сравнение строк в данном случае
    }

    private String getString(Iterable<String> iterable) {
        // how to ??
        StringBuilder result = new StringBuilder();
        for (String element : iterable) {
            result.append(element + " ");
        }
        return result.toString();
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

    private String tryIdentifyUser(String password, Session session) throws IOException {
        String login = session.enteredLogin;
        if (userManager.isUserInDB(login)) {
            if (userManager.isCorrectPassword(login, password)) {
                session.askForPassword = false;
                session.user = userManager.getUser(session.enteredLogin);
                return BotMessages.youLogInAs + login;
            }
            return BotMessages.incorrectPassword;
        }
        session.askForPassword = false;

        session.user = userManager.createUser(login, password);
        return BotMessages.profileHasCreated; // + session.user.Login; - пока что так, для кнопок
    }

    public List<String> createButtons(String text){
        List<String> listButton = new ArrayList<>();
        listButton.add("/help");

        List<String> fullCommandSet= new ArrayList<>();
        fullCommandSet.add("/login");
        fullCommandSet.add("/create");
        fullCommandSet.add("/play");
        fullCommandSet.add("/score");
        fullCommandSet.add( "/stop");
        fullCommandSet.add( "/exit");
        fullCommandSet.add( "/me");
        fullCommandSet.add( "/movies");
        fullCommandSet.add( "/friends");
        fullCommandSet.add( "/add");
        fullCommandSet.add( "/save");
        fullCommandSet.add("/help");

        switch (text) {
            case instruction:
                listButton.add("/login");
                listButton.add("/create");
                return listButton;
            case help:
                return fullCommandSet;

            case BotMessages.emptyLogin: //Можно добавить кнопки рандом на ник и пароль
                listButton.add("/create"); //ник ещё может быть занят
                return listButton;
            case BotMessages.profileHasCreated:
                listButton.add("/play");
                listButton.add("/me");
                listButton.add("/score");
                return listButton;

            case "Ok":
                listButton.add("play");
                listButton.add("/exit");
                return listButton;

            default:
                return fullCommandSet;
        }
    }
}
