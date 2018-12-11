package Test;

import Bot.Bot;
import Bot.QuizBot;
import User.UserManagerForTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSelfInfoTest {

    Bot bot;
    private GetSelfInfoTest() throws IOException {
//        var testData = new HashMap<String, String>(){{
//            put("question", "answer");
//            put("question1", "answer1");}};
        bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
    }

    @Test
    public void canGetScore() throws IOException {
        var output = bot.processInput("/score", 0);
        assertEquals("0", output.get(0));
    }
    @Test
    public void canGetMovies() throws IOException {
        var output = bot.processInput("/movies", 0);
        assertEquals("[]", output.get(0));
    }
    @Test
    public void canGetFriends() throws IOException {
        var output = bot.processInput("/friends", 0);
        assertEquals("[]", output.get(0));
    }
}
