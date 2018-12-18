package bot.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager implements IUserManager {
    //    private HashMap<String, User> dataBase;
    //private HashSet<String> changed;
    private static UserManager instance;
    private ConcurrentHashMap<String, User> dataBase;


    UserManager() throws IOException {
        dataBase = new ConcurrentHashMap<>(UsersSaver.getAllUsers());
        //dataBase = new HashMap<>(UsersSaver.getAllUsers());
    }

    public synchronized static UserManager getInstance() throws IOException {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }


    public boolean isUserInDB(String userLogin) {
        return dataBase.containsKey(userLogin);
    }

    public User createUser(String login, String password) throws IOException {
        User user = new User(login, password);
        dataBase.put(login, user);
//        User.UsersSaver.saveUser(user);
        return user;
    }

    public boolean isCorrectPassword(String login, String password) {
        User user = getUser(login);
        return user.getPasswordHash() == password.hashCode();
    }

    public User getUser(String userName) {//mb write if not return null
        return dataBase.get(userName);
    }

    public void addFriendToUser(String userLogin, String friendLogin) {
        User user = dataBase.get(userLogin);
//        User.User friend = dataBase.get(friendLogin);
        user.Friends.add(friendLogin);
    }

    public boolean areFriends(User user1, User user2) {
        return user1.Friends.contains(user2.getLogin()) && user2.Friends.contains(user1.getLogin());
    }

    public boolean areFriends(String userLogin1, String userLogin2) {
        var user1 = getUser(userLogin1);
        var user2 = getUser(userLogin2);
        return user1.Friends.contains(user2.getLogin()) && user2.Friends.contains(user1.getLogin());
    }

    public void saveChanges() throws IOException {
        for (String userLogin : dataBase.keySet()) {
            UsersSaver.saveUser(dataBase.get(userLogin));
        }
    }

    public boolean hasUserPermission(String login, String anotherLogin) {
        User user = getUser(login);
        User anotherUser = getUser(anotherLogin);
        return hasUserPermission(user, anotherUser);
    }

    public boolean hasUserPermission(User user, User anotherUser) {
        return areFriends(user, anotherUser);
    }


}