package bot.User;

import java.util.HashSet;

public class User {
    public String Login;
    public int passwordHash;

    public HashSet<String> Known = new HashSet<String>();

    public HashSet<String> Friends = new HashSet<>();

    private int score;

    User(String login, String password) {
        this.Login = login;
        this.passwordHash = password.hashCode();
    }

    User() {
    }

    public int getScore() {
        return this.Known.size();
    }
}
