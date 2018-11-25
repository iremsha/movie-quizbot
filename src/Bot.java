import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Bot implements IBot {

    private String instruction = "If you've already played enter '/login' and your login\n" +
            "Else enter '/create' and your login\n" +
            "If you want some more information enter '/help\n";

    private IQuizBot quizBot;
    private String help = "/start - start game \n" + //дописать команды
            "/score - show current score \n" +
            "/stop - stop game \n" +
            "/help - help?";

    private IUserManager userManager;
    public HashMap<Integer, Session> sessions = new HashMap<>(); //why can't int
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


    public ArrayList<String> processInput(String userInput, int sessionId) throws IOException {
        ArrayList<String> listMsg = new ArrayList<>();
        Session session = sessions.get(sessionId);
        if (session == null) {
            sessions.put(sessionId, new Session());
        }
        String command = getCommand(userInput);
        String argument = getArgument(userInput);

        if (isPriorityCommand(command)) {
            listMsg.add(processCommand(command, argument, sessionId));
            return listMsg;
        }
        if (session.askForPassword) {
            listMsg.add(tryIdentifyUser(argument, session));
            return listMsg;
        }
        if (session.user == null) {
            listMsg.add("Need log in. Enter '/login' and your login or /create and new login");
            return listMsg;
        }
        if (session.playing) {
            String quizBotAnswer = quizBot.analyzeUserAnswer(session.lastOfferedQuestion, userInput, session.user);
            String nextQuestion = quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = nextQuestion;
            //return quizBotAnswer + "\n" + nextQuestion;
            listMsg.add(quizBotAnswer);
            listMsg.add(nextQuestion);
            return listMsg;
        }
        if (isCommand(command)) {
            listMsg.add(processCommand(command, argument, sessionId));
            return listMsg;
        }
        listMsg.add("Unexpected input. Try '/help'");
        return listMsg;
    }

    private String processCommand(String command, String argument, int sessionId) throws IOException {
        Session session = sessions.get(sessionId);
        session.askForPassword = false;
        switch (command) {
            case "/start":
                return instruction;
            case "/help":
                return help;
            case "/create":
                return processCommandCreate(argument, session);
            case "/login":
                return processCommandLogin(argument, session);
            case "/score":
                return processCommandScore(argument, session);
            case "/movies": //later add watch friends' movies
                return processCommandMovies(argument, session);
            case "/friends":
//                var r = from().where("name", eq("Arthur"))
                return getString(session.user.Friends);
            case "/add":
                return processCommandAddFriend(argument, session);
            case "/play":
                session.playing = true;
                String firstQuestion = quizBot.getQuestionToOffer(session.user);
                session.lastOfferedQuestion = firstQuestion;
                return quizBot.getInstruction() + "\n" + firstQuestion;
            case "/me":
                return session.user.Login;
            case "/stop":
                session.playing = false;
                return "Ok";
            case "/exit":
                session.user = null;
                return "bye";
            case "/save":
                userManager.saveChanges();
                return "done";
            default:
                return "Incorrect command";
        }
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
            return "No user with this login";
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

    private String processCommandLogin(String login, Session session) {
        if (login.isEmpty()) {
            return "Empty login";
        }
        session.user = null;
        if (!userManager.isUserInDB(login)) {
            return "No users with this login. Try again";
        }
        session.enteredLogin = login;
        session.askForPassword = true;
        return "Enter password";
    }

    private String processCommandAddFriend(String argument, Session session) {
        String login = argument;
        if (login.equals("")) {
            return "Need login after command";
        }
        if (login.equals(session.user.Login)) {
            return "You can't add yourself";
        }
        if (!userManager.isUserInDB(login)) {
            return "No user with this login";
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
            return input.split("\\s", 2)[0];
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
                return "You log in as " + login;
            }
            return "Incorrect password. Try again";
        }
        session.askForPassword = false;
        session.user = userManager.createUser(login, password);
        return "Profile has created. You log in as " + session.user.Login;
    }
}
