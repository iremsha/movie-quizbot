package bot.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class UserManagerForTest implements IUserManager {
    private ConcurrentHashMap<String, User> dataBase;

    private UserManagerForTest() throws IOException {
        super();
        this.dataBase = new ConcurrentHashMap<>();
    }

    public synchronized static UserManagerForTest getInstance() throws IOException {
        return new UserManagerForTest();
    }

    public boolean isUserInDB(String userLogin) {
        return dataBase.containsKey(userLogin);
    }

    public User createUser(String login, String password) throws IOException {
        User user = new User(login, password);
        dataBase.put(login, user);
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