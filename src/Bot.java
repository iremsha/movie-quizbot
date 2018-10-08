class Bot {

    private String instruction;
    private Player player;
    private QuizBot quizBot;
    private String help;
    String lastMessage;



    Bot(String instruction, String help, Player player, QuizBot quizBot){
        this.instruction = instruction;
        this.player = player;
        this.quizBot = quizBot;
        this.help = help;
    }

    String getStartMessage(){
        return instruction;
    }

    String getNextMessage(){
        return quizBot.getQuestionToOffer(player);
    }

    String processInput(String lastMessage, String userInput, Player currentPlayer){
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
