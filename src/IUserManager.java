public interface IUserManager {

    boolean isUserInDB(String userLogin);
//    User createUser(String login);
    public User createUser(String login, String password);

    User getUser(String userName);
    void addFriendToUser(String userLogin, String friendLogin);
}
