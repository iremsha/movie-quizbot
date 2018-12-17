//package User;
//
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class UserManagerForTest extends UserManager {
//    private ConcurrentHashMap<String, User> dataBase;
//    private static UserManagerForTest instance;
//
//    private UserManagerForTest() throws IOException {
//        super();
//        this.dataBase = new ConcurrentHashMap<>();
//    }
////    public synchronized static UserManagerForTest getInstance() throws IOException {
//////        if (instance == null) {
//////            instance = new UserManagerForTest();
//////        }
//////        return instance;
//////    }
//    public synchronized static UserManagerForTest getInstance() throws IOException {
//        return new UserManagerForTest();
//    }
//    public synchronized static UserManagerForTest getNewInstance() throws IOException {
//        return new UserManagerForTest();
//    }
//
//
//    public void saveChanges() throws IOException {
//    }
//
//
//}
