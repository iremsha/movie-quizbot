package Bot;
import User.*;
import java.util.List;

public class Session {
    public int Id;
    public User user;
    public boolean playing;
    public String lastOfferedQuestion;
    public String enteredLogin;
    public boolean askForPassword;
    public boolean askForLogin;
    public List<String> availableCommands;
    public List<String> toButtonsCommands;
}
