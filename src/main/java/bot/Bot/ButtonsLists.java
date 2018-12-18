package bot.Bot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ButtonsLists {
    public static List<String> whenUserNull(){return Collections.unmodifiableList(Arrays.asList("login", "create"));    }
    public static List<String> whenPlaying(){return Collections.unmodifiableList(Arrays.asList("stop", "score"));    }
    public static List<String> whenNoPlaying(){
        return Collections.unmodifiableList(Arrays.asList("play", "score"));
    }
    public static List<String> whenWaitForPassword(){
        return Collections.unmodifiableList(Arrays.asList());
    }
}
