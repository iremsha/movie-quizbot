import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class QuizBotTest {
    private HashMap<String, String> data = new HashMap<>() {{
        put("Pulp Fiction", "1");
        put("A Clockwork Orange", "2");
        put("Blue Velvet", "3");
    }};
    private Player elmo = new Player("Elmo");
    private QuizBot quizBot = new QuizBot(data);
    private String goodJobMessage = "Good job";


    @Test
    public void analyzeUserAnswerCommand() {
        assertEquals("Bad job", quizBot.analyzeUserAnswer("", "\\start", elmo));
    }

    @Test
    public void analyzeUserAnswerBadAnswer() {
        assertEquals("Bad job", quizBot.analyzeUserAnswer("", "Remsha", elmo));
    }

    @Test
    public void analyzeUserAnswerGoodAnswer() {
        assertEquals("Bad job", quizBot.analyzeUserAnswer("", "", elmo));
    }

    @Test
    public void getQuestionToOffer() {
        assertTrue(data.containsKey(quizBot.getQuestionToOffer(elmo)));
    }

    @Test
    public void addScore(){
        assertEquals(elmo.score + 1, quizBot.addScore(elmo));
    }

    @Test
    public void addKnowQuestion(){
        assertFalse(quizBot.addKnowQuestion(elmo, "EX").isEmpty());
    }
}