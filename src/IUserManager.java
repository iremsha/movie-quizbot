import java.io.IOException;

public interface IUserManager {

    boolean isUserInDB(String userLogin);
    User createUser(String login, String password) throws IOException;
    boolean isCorrectPassword(String login, String password);
    User getUser(String userName);
    void addFriendToUser(String userLogin, String friendLogin);
    boolean areFriends(User user1, User user2);
    boolean areFriends(String user1, String user2);
    void saveChanges() throws IOException;
    boolean hasUserPermission(User user, User anotherUser);
    boolean hasUserPermission(String user, String anotherUser);
}
