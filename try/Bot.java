import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

class Bot implements IBot {

    public String instruction = "If you've already played enter '/login' and your login\n" +
            "Else enter '/create' and your login\n" +
            "If you want some more information enter '/help\n";

    public IQuizBot quizBot;

    public HashMap<String, Command> commands = CommandsCreator.getCommandsHashMap();

    public IUserManager userManager;
    public ConcurrentHashMap<Integer, Session> sessions = new ConcurrentHashMap<>(); //why can't int

    Bot(IQuizBot quizBot, IUserManager userManager) {
        this.quizBot = quizBot;
        this.userManager = userManager;
    }

    public ArrayList<String> processInput(String userInput, int sessionId) throws IOException {
        ArrayList<String> listMsg = new ArrayList<>();
        Session session = sessions.get(sessionId);
        if (session == null){
            sessions.put(sessionId, new Session());
        }
        String command = getCommand(userInput);
        String argument = getArgument(userInput);
        if (!command.isEmpty()) {
            listMsg.add(processCommand(command, argument, sessionId));
        }
        else if (session.askForPassword){
            listMsg.add(tryIdentifyUser(argument, session));
        }
        else if (session.user == null) {
            listMsg.add("Need log in. Enter '/login' and your login or /create and new login");
        }
        else if (session.playing) {
            String quizBotAnswer = quizBot.analyzeUserAnswer(session.lastOfferedQuestion, userInput, session.user);
            String nextQuestion = quizBot.getQuestionToOffer(session.user);
            session.lastOfferedQuestion = nextQuestion;
            listMsg.add(quizBotAnswer);
            listMsg.add(nextQuestion);
        }
        else {listMsg.add("Unexpected input. Try '/help'");}
        return listMsg;
    }

    private String processCommand(String command, String argument, int sessionId) throws IOException {
        Session session = sessions.get(sessionId);
//        session.askForPassword = false; ???
        return commands.get(command).Execute.commandFunction(this, argument, session);

        //        switch (command) {
//            case "/start":
//                return instruction;
//            case "/help":
//                return help;
//            case "/create":
//                return commands.get("start").Execute.commandFunction(this, "", session);
////            return processCommandCreate(argument, session);
//            case "/login":
//                return processCommandLogin(argument, session);
//            case "/score":
//                return processCommandScore(argument, session);
//            case "/movies": //later add watch friends' movies
//                return getString(session.user.Known);
//            case "/friends":
////                var r = from().where("name", eq("Arthur"))
//                return getString(session.user.Friends);
//            case "/add":
//                return processCommandAddFriend(argument, session);
//            case "/play":
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
//                return "Incorrect command";
//        }
    }


    private String getCommand(String input) {
        if (input.startsWith("/")){
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
