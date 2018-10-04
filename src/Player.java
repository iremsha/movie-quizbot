import java.util.HashSet;
import java.util.List;

class Player {
    String name;
    int score = 0;
    HashSet<String> KnownMovies = new HashSet<String >();
    HashSet<String> Failures = new HashSet<String >();
    boolean wantsToPlay = true;

    Player(String name){
        this.name = name;
    }
}
