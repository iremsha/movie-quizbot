package bot.Bot;

import bot.User.User;

import java.util.List;

public class Session {

    private User user;
    private UserAction action;
    private String lastOfferedQuestion;
    private boolean askForLoginAndPassword;
    private List<String> toButtonsCommands;

    public Session() {
    }

    public Session(User user, UserAction action, String lastOfferedQuestion,
                   boolean askForLoginAndPassword, List<String> toButtonsCommands) {
        this.user = user;
        this.action = action;
        this.lastOfferedQuestion = lastOfferedQuestion;
        this.askForLoginAndPassword = askForLoginAndPassword;
        this.toButtonsCommands = toButtonsCommands;
    }

    public User getUser() {
        return user;
    }

    public UserAction getAction() {
        return action;
    }

    public String getLastOfferedQuestion() {
        return lastOfferedQuestion;
    }

    public boolean getAskForLoginAndPassword() {
        return askForLoginAndPassword;
    }

    public List<String> getToButtonsCommands() {
        return toButtonsCommands;
    }


}