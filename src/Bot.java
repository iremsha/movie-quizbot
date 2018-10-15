class Bot implements IBot {

    private String instruction;
    private Player player;
    private IQuizBot quizBot;
    private String help;

//    Bot(String instruction, String help, String userName, IQuizBot quizBot){
//        this.instruction = instruction;
//        this.player = Player(userName);
//        this.quizBot = quizBot;
//        this.help = help;
//    }

    Bot(String instruction, String help, Player player, IQuizBot quizBot){
        this.instruction = instruction;
        this.player = player;
        this.quizBot = quizBot;
        this.help = help;
    }


    public String getStartMessage(){
        return instruction;
    }

    public String getNextMessage(){
        return quizBot.getQuestionToOffer(player);
    }

    public String processInput(String lastMessage, String userInput, Player currentPlayer){
        if (userInput.equals(""))
            return "";
        if (isCommand(userInput))
            return processCommand(userInput, currentPlayer);
        return quizBot.analyzeUserAnswer(lastMessage, userInput, player);
    }


    boolean isCommand(String userInput){
//        return COMMANDS.containsKey(userInput);
        return userInput.startsWith("\\");
    }

    String processCommand(String command, Player currentPlayer){
        switch(command) {
            case "\\start" :
                return instruction;

            case "\\help" :
                return help;

            case "\\score" :
                return String.valueOf(currentPlayer.score);

            case "\\stop" :
                currentPlayer.wantsToPlay = false;
                break;

            default :
                return "Incorrect command";
        }
        return "";
    }
}
