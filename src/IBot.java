import java.io.IOException;
import java.util.List;

public interface IBot {

    List<String> processInput(String userInput, int sessionId) throws IOException;

}
