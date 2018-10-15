public interface IBot {

    String getStartMessage();
    String getNextMessage();
    String processInput(String lastMessage, String userInput, Player currentPlayer);

}
