import java.io.IOException;

public interface IBot {

    String getStartMessage(int sessionId);
    String processInput(String userInput, int sessionId) throws IOException;

}
