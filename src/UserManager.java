import java.util.HashMap;

public class UserManager implements IUserManager {
    private HashMap<String, User> dataBase = new HashMap<>();
    public UserManager(){

    }

    public boolean isUserInDB(String userLogin){
            return dataBase.containsKey(userLogin);
    }
//    public User createUser(String login){
//        User user = new User(login);
//        dataBase.put(login, user);
//        return user;
//    }
    public User createUser(String login, String password){
        User user = new User(login, password);
        dataBase.put(login, user);
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
        User friend = dataBase.get(friendLogin);
        //см что если ключа нет в словаре
        user.Friends.add(friend);
    }

    public boolean areFriends(User user1, User user2){
        return user1.Friends.contains(user2) && user2.Friends.contains(user1);
    }

}
