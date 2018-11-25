import java.util.List;

public class Session {
    int Id;
    User user;
    boolean playing;
    String lastOfferedQuestion;
    String enteredLogin;
    boolean askForPassword;
    List<String> availableCommands;
    List<String> toButtonsCommands;
}
