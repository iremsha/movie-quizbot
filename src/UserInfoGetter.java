public class UserInfoGetter {
    public static String get(UserInfo userInfo, User user){
        switch (userInfo){
            case Score: return String.valueOf(user.getScore());
            case Movies: return user.Known.toString();
            case Friends: return user.Friends.toString();
            default: return null;
        }
    }
}
