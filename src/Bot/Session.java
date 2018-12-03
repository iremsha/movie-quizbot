package Bot;
import User.*;
import java.util.List;

public class Session {
    public int Id;
    public User user;
    public UserAction action;
//    public boolean playing;
    public String lastOfferedQuestion;
    public String enteredLogin;
    public boolean askForLoginAndPassword;
//    public boolean askForLogin;
    public List<String> availableCommands;
    public List<String> toButtonsCommands;
}
