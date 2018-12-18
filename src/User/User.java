package User;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    private String login;
    public String getLogin(){return login;}

    private int passwordHash;
    public int getPasswordHash(){return passwordHash;}

//    public HashSet<String> Known = new HashSet<String >();
//    public HashSet<String> Friends = new HashSet<>();

    public Set<String> Known = ConcurrentHashMap.newKeySet();
    public Set<String> Friends = ConcurrentHashMap.newKeySet();

    private int score;
    public int getScore(){
        return this.Known.size();
    }

    User(String login, String password){
        this.login = login;
        this.passwordHash = password.hashCode();
    }
    User(){}

}
