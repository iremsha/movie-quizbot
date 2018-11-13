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

    private UserManager userManager;
    private HashMap<Integer, Session> sessions = new HashMap<>(); //why can't int

    Bot(IQuizBot quizBot, UserManager userManager) {
        this.quizBot = quizBot;
        this.userManager = userManager;
    }

    public String getStartMessage(int sessionId) {
        sessions.put(sessionId, new Session());
        return instruction;
    }

    public String processInput(String userInput, int sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null){
            sessions.put(sessionId, new Session());
        }
        String command = getCommand(userInput);
        String argument = getArgument(userInput);

        if (isPriorityCommand(command)) {
            return processCommand(command, argument, sessionId);
        }
        if (session.askForPassword){
            return tryIdentifyUser(argument, session);
        }
        if (session.user == null) {
            return "Need log in. Enter '/login' and your login or /create and new login";
        }
        if (session.playing) {
            String quizBotAnswer = quizBot.analyzeUserAnswer(session.lastOfferedQuestion, userInput, session.user);
            String nextQuestion = quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = nextQuestion;
            return quizBotAnswer + "\n" + nextQuestion;
        }
        if (isCommand(command)) {
            return processCommand(command, argument, sessionId);
        }
        return "Unexpected input. Try '/help'";
    }

    private String processCommand(String command, String argument, int sessionId) {
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
                return getString(session.user.Known);
            case "/friends":
//                var r = from().where("name", eq("Arthur"))
                return getLogins(session.user.Friends);
            case "/add_friend":
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
            default:
                return "Incorrect command";
        }
    }

    private String processCommandScore(String login, Session session) {
        if (login.equals("") || login.equals(session.user.Login)){
//            return Integer.toString(session.user.score);
            return String.valueOf(session.user.getScore());
        }
        if (!userManager.isUserInDB(login)){
            return "No user with this login";
        }
        if (!hasUserPermission(userManager, session.user.Login, login)){
            return "Permission denied";
        }
        return String.valueOf(userManager.getUser(login).getScore());
    }

    private String processCommandCreate(String login, Session session) {
        if (login.isEmpty()){
            return "Empty login";
        }
//        session.user = null;
        if (userManager.isUserInDB(login)){
            return "This login has already taken";
        }
        session.enteredLogin = login;
        session.askForPassword = true;
        return "Enter password";
    }
    private String processCommandLogin(String login, Session session) {
        if (login.isEmpty()){
            return "Empty login";
        }
        session.user = null;
        if (!userManager.isUserInDB(login)) {
            return "No users with this login. Try again";
        }
//                session.user = userManager.getUser(argument);
        session.enteredLogin = login;
        session.askForPassword = true;
        return "Enter password"; // should be in spec method
//                return "you've log in as " + session.user.Login;
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
        userManager.addFriendToUser(session.user.Login, login);
        return "Done";
    }

    private boolean hasUserPermission(UserManager userManager, String login, String anotherLogin) {
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
        List<String> priorityCommands = Arrays.asList(new String[]{"/start", "/help", "/stop", "/create", "/log_in"});
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
    private String getLogins(Iterable<User> iterable) {
        StringBuilder result = new StringBuilder();
        for (User element : iterable) {
            result.append(element.Login + " ");
        }
        return result.toString();
    }

    private String getCommand(String input) {
        if (input.startsWith("/")){
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

    private String tryIdentifyUser(String password, Session session){
        String login = session.enteredLogin;
        if (userManager.isUserInDB(login)){
            if (userManager.isCorrectPassword(login, password)){
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
