import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class UserManager implements IUserManager {
    private HashMap<String, User> dataBase;
    private HashSet<String> changed;

    public UserManager() throws IOException {
        dataBase = UsersSaver.getAllUsers();
    }

    public boolean isUserInDB(String userLogin){
        return dataBase.containsKey(userLogin);
    }

    public User createUser(String login, String password) throws IOException {
        User user = new User(login, password);
        dataBase.put(login, user);
//        UsersSaver.saveUser(user);
        return user;
    }
    public boolean isCorrectPassword(String login, String password){
        User user = getUser(login);
        return user.passwordHash == password.hashCode();
    }

    public User getUser(String userName){//mb write if not return null
        return dataBase.get(userName);
    }
    public void addFriendToUser(String userLogin, String friendLogin){
        User user = dataBase.get(userLogin);
//        User friend = dataBase.get(friendLogin);
        user.Friends.add(friendLogin);
    }

    public boolean areFriends(User user1, User user2){
        return user1.Friends.contains(user2.Login) && user2.Friends.contains(user1.Login);
    }

    public void saveChanges() throws IOException {
        for(String userLogin:dataBase.keySet()){
            UsersSaver.saveUser(dataBase.get(userLogin));
        }
    }


}
