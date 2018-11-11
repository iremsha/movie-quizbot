import java.util.HashSet;

public class User {
    public String Login;
    int passwordHash;

    public HashSet<String> Known = new HashSet<String >();

    public HashSet<User> Friends = new HashSet<>();
    public HashSet<String> FriendsLogins;

    public int Score;
    public int getScore(){
        return Known.size();
    }

    User(String login){
        this.Login = login;
    }
}
