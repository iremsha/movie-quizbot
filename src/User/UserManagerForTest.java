package User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class UserManagerForTest extends UserManager {
    private ConcurrentHashMap<String, User> dataBase;
    private static UserManagerForTest instance;

    private UserManagerForTest() throws IOException {
        super();
        this.dataBase = new ConcurrentHashMap<>();
    }

    public synchronized static UserManagerForTest getInstance() throws IOException {
        return new UserManagerForTest();
    }
    public synchronized static UserManagerForTest getNewInstance() throws IOException {
        return new UserManagerForTest();
    }
//
//
//    @Override
//    public boolean isUserInDB(String userLogin) {
//        return false;
//    }
//
//    @Override
//    public User.User createUser(String login, String password) throws IOException {
//        return null;
//    }
//
//    @Override
//    public boolean isCorrectPassword(String login, String password) {
//        return false;
//    }
//
//    @Override
//    public User.User getUser(String userName) {
//        return null;
//    }
//
//    @Override
//    public void addFriendToUser(String userLogin, String friendLogin) {
//
//    }
//
//    @Override
//    public boolean areFriends(String user1, String user2) {
//        return false;
//    }
//
//    @Override
//    public boolean areFriends(User.User user1, User.User user2) {
//        return false;
//    }
//
//    public void saveChanges() throws IOException {
//    }
//
//    @Override
//    public boolean hasUserPermission(String user, String anotherUser) {
//        return false;
//    }
//
//    @Override
//    public boolean hasUserPermission(User.User user, User.User anotherUser) {
//        return false;
//    }


}
