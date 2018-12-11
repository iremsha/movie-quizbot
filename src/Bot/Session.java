package Bot;

import User.User;

import java.util.ArrayList;
import java.util.List;

public class Session {
    public int Id;
    public User user;
    public UserAction action;
    public String lastOfferedQuestion;
    public boolean askForLoginAndPassword;
    public List<String> toButtonsCommands = new ArrayList<>();

    

}
