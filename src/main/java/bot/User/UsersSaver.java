package bot.User;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class UsersSaver {

    //    private static String path = "users.json";
    private static String folderPath = "users";

    public static void saveUser(User user) throws IOException {
        String filePath = String.format("%s\\%s.json", folderPath, user.getLogin());
//        System.out.println(filePath);
        if (!Files.exists(Paths.get(filePath))) Files.createFile(Paths.get(filePath));
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filePath), user);
//        String userString = mapper.writeValueAsString(user);
        System.out.println(Files.readAllLines(Paths.get(filePath)));
    }

    public static User getUser(String login) throws IOException {
        String filePath = String.format("%s\\%s.json", folderPath, login);
        if (!Files.exists(Paths.get(filePath))) return null;
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File(filePath), User.class);
        return user;
    }

    public static HashMap<String, User> getAllUsers() throws IOException {
        HashMap<String, User> hashMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (var file : new File(folderPath).listFiles()) {
            User nextUser = mapper.readValue(file, User.class);
            hashMap.put(nextUser.getLogin(), nextUser);
        }
        return hashMap;
    }

    public static void main(String[] args) throws IOException {
        UsersSaver.saveUser(new User("gt", "a"));
        User user = new User("a", "a");
        user.Known.add("gg");
        user.Known.add("gd");
        UsersSaver.saveUser(user);
        var r = UsersSaver.getAllUsers();
        System.out.println(UsersSaver.getUser("gt"));

    }
}