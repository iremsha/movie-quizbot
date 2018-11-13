import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DataSaver {
    public static void save(String path, User user) throws IOException {
//        if (!path.endsWith(".json")){
////            throw new ParamException.FormParamException();
//            System.out.println("Exception");
//        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("D:\\file.json"), user);
//        String jsonInString = mapper.writeValueAsString(obj);
    }

//    public static HashMap<String, String> getData(String path) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//
//        User user = mapper.readValue(new File("D:\\file.json"), User.class);
//
//    }
    public static User getData(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        User user = mapper.readValue(new File("D:\\file.json"), User.class);
        return user;

    }
}
