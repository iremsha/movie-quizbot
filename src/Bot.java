import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Bot implements IBot {

    private String instruction = "If you've already played enter '/log_in' and your login\n" +
            // может login and password separated by space
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
        String command = getCommand(userInput);
        String argument = getArgument(userInput);
        if (isPriorityCommand(command)) {
            return processCommand(command, argument, sessionId);
        }
        if (session.user == null) {
//            session.userLogin = userInput;
            return "need to log in. Enter '/log_in' and your login or /create and new login";
        }
        if (session.playing) {
            String quisBotAnswer = quizBot.analyzeUserAnswer(session.lastOfferedQuestion, userInput, session.user);
            String nextQuestion = quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = nextQuestion;
            return quisBotAnswer + "\n" + nextQuestion;
        }
        if (isCommand(command)) {
            return processCommand(command, argument, sessionId);
        }
        return "Unexpected input. Try '/help'";
    }


    boolean isCommand(String userInput) {
//        return COMMANDS.containsKey(userInput);
        return userInput.startsWith("/");
    }

    private boolean isPriorityCommand(String input) {
        List<String> priorityCommands = Arrays.asList(new String[]{"/start", "/help", "/stop", "/create", "/log_in"});
        return priorityCommands.contains(input); //проверить сравнение строк в данном случае
    }

    private String processCommand(String command, String argument, int sessionId) {
        Session session = sessions.get(sessionId);
        switch (command) {
            case "/start":
                return instruction;
            case "/help":
                return help;
            case "/create":
                if (userManager.isUserInDB(argument)){
                    return "This login has already taken";
                }
                session.user = userManager.createUser(argument);
                return "User created";
            case "/log_in":
                if (!userManager.isUserInDB(argument)) {
                    return "No users with this login. Try again";
                }
                session.user = userManager.getUser(argument);
                return "you've log in as " + session.user.Login;
            case "/score":
                return processCommandScore(argument, session);
            case "/movies": //later add watch friends' movies
                return getString(session.user.Known);
            case "/friends":
//                var r = from().where("name", eq("Arthur"))
                return getLogins(session.user.Friends);
            case "/add_friend":
                return processCommandAddFriend(argument, session); //передавать не id а конкретную сессию или поля
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

    private String processCommandScore(String argument, Session session) {
        String login = argument;
        if (login.equals("") || login.equals(session.user.Login)){
            return Integer.toString(session.user.Score);
        }
        if (!userManager.isUserInDB(login)){
            return "No user with this login";
        }
        if (!hasUserPermission(userManager, session.user.Login, login)){
            return "Permission denied";
        }
        return String.valueOf(userManager.getUser(login).Score);
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
        return input.split("\\s", 2)[0];
    }
    private String getArgument(String input) {
        String[] arrInput = input.split("\\s", 2);
        return (arrInput.length < 2) ? "" : arrInput[1];
    }
}
