package bot.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoGetter {
    public static String get(UserInfo userInfo, User user) throws IOException {
        switch (userInfo) {
            case Score:
                return String.valueOf(user.getScore());
            case Movies:
                return user.Known.toString();
            case Friends:
                return getTrueFriends(user).toString();
            default:
                return null;
        }
    }

    private static List<String> getTrueFriends(User user) throws IOException {
        UserManager userManager = UserManager.getInstance();
        List<String> trueFriends = new ArrayList<>();
        var follows = user.Friends;
        for (var followName : follows) {
            var followUser = userManager.getUser(followName);
            if (followUser.Friends.contains(user.getLogin())){
                trueFriends.add(followName);
            }
        }
        return trueFriends;
    }
}