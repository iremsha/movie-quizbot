package Test;

import Bot.Bot;
import Bot.QuizBot;
import Bot.UserAction;
import User.UserManagerForTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlayingTest {
    Bot bot;
    private PlayingTest() throws IOException {
        var testData = new HashMap<String, String>(){{
            put("question", "answer");
            put("question1", "answer1");}};
        var quizBot = new QuizBot(testData);
        bot = new Bot(quizBot, UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
    }

    @Test
    public void canPlay() throws IOException {
        bot.processInput("/play", 0);
        assertEquals(UserAction.play, bot.sessions.get(0).action);
    }

    @Test
    public void canStop() throws IOException {
        bot.processInput("/play", 0);
        bot.processInput("/stop", 0);
        assertNull(bot.sessions.get(0).action);
    }

    @Test
    public void getScoreInfo() throws IOException {
        var output = bot.processInput("/score", 0);
        assertEquals("0", output.get(0));
    }

    @Test
    public void increaseScoreIfRightAnswer() throws IOException {
        var scoreBefore = bot.sessions.get(0).user.getScore();
        bot.processInput("/play", 0);
        bot.processInput("answer", 0);
        var scoreAfter = bot.sessions.get(0).user.getScore();
        assertEquals(1, scoreAfter - scoreBefore);
    }

    @Test
    public void notIncreaseScoreIfWrongAnswer() throws IOException {
        var scoreBefore = bot.sessions.get(0).user.getScore();
        bot.processInput("/play", 0);
        bot.processInput("wrongAnswer", 0);
        var scoreAfter = bot.sessions.get(0).user.getScore();
        assertEquals(0, scoreAfter - scoreBefore);
    }
}
