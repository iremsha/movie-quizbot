import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BotTest {
    private HashMap<String, String> data = new HashMap<String, String>(){{
        put("Pulp Fiction", "1");
        put("A Clockwork Orange", "2");
        put("Blue Velvet", "3");
    }};
    private String botInstruction = "I'd like you to play the game!\n" +
            "I will send you the name of movie and you'll name the director." +
            "You've got 3 attemps for every movie\n";

    private String botHelp = "\\start - start game \n" +
            "\\score - show current score \n" +
            "\\stop - stop game \n" +
            "\\help - help?";
    private Player elmo = new Player("Elmo");
    private QuizBot quizBot = new QuizBot(data, elmo);
    private Bot bot = new Bot(botInstruction, botHelp, elmo, quizBot);

    @Test
    public void getStartMessage() {
        assertEquals(botInstruction, bot.getStartMessage());
    }

    @Test
    public void getNextMessage() {
        assertTrue(data.containsKey(quizBot.getQuestionToOffer(elmo)));    }

    @Test
    public void processInputHelp() {
        assertEquals(bot.processCommand("\\help", elmo), bot.processInput("","\\help", elmo));
    }

    @Test
    public void processInputStop() {
        assertEquals(bot.processCommand("\\stop", elmo), bot.processInput("", "\\stop", elmo));
    }

    @Test
    public void processInputStart() {
        assertEquals(bot.processCommand("\\start", elmo), bot.processInput("", "\\start", elmo));
    }

    @Test
    public void processInputScore() {
        assertEquals(bot.processCommand("\\score", elmo), bot.processInput("", "\\score", elmo));
    }

    @Test
    public void processInputNoCommand() {
        assertEquals(quizBot.analyzeUserAnswer("", "Elmo", elmo), bot.processInput("", "Elmo", elmo));
    }

    @Test
    public void processInputNotFoundCommand() {
        assertEquals("Incorrect command", bot.processInput("", "\\PAMAGITE", elmo));
    }


    @Test
    public void processCommandStart() {
        assertEquals(botInstruction, bot.processCommand("\\start", elmo));
    }

    @Test
    public void processCommandHelp() {
        assertEquals(botHelp, bot.processCommand("\\help", elmo));
    }

    @Test
    public void processCommandScore() {
        assertEquals(String.valueOf(elmo.score), bot.processCommand("\\score", elmo));
    }

    @Test
    public void processCommandStop() {
        assertEquals("", bot.processCommand("\\stop", elmo));
    }

    @Test
    public void isCommand() {
        assertTrue(bot.isCommand("\\"));
    }
}