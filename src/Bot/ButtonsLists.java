package Bot;

import java.util.Arrays;
import java.util.List;

public class ButtonsLists {
    public static List<String> whenUserNull(){
        return Arrays.asList("login", "create");
    }
    public static List<String> whenPlaying(){
        return Arrays.asList("stop", "score");
    }
    public static List<String> whenNoPlaying(){
        return Arrays.asList("play", "score");
    }
    public static List<String> whenWaitForPassword(){
        return Arrays.asList();
    }
}
