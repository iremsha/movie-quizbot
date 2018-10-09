import java.util.HashSet;

class Player {
    private String name;
    int score = 0;
    HashSet<String> Known = new HashSet<String >();
    HashSet<String> Failures = new HashSet<String >();
    boolean wantsToPlay = true;

//    String lastOfferedQuestion;

    Player(String name){
        this.name = name;
    }

    public static void increaseScore(Player player){
        player.score += 1;
    }

    public String getScore(){
        return "Your score: " + score;
    }


}
