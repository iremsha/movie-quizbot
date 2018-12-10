package Test;

import Bot.*;
import User.UserManager;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithoutLoginTest {
    Bot bot = new Bot(new QuizBot(null), UserManager.getInstance());

    private WithoutLoginTest() throws IOException {
    }

    @Test
    public void canGetHelp() throws IOException {
        var output = bot.processInput("/help", 0);
        assertEquals(bot.help, output.get(0));
    }
    @Test
    public void canGetInstruction() throws IOException {
        var output = bot.processInput("/start", 0);
        assertEquals(bot.instruction, output.get(0));
    }

//    @Test
//    public void cantPlay() throws IOException {
//        var output = bot.processInput("/play", 0);
//        assertEquals(BotMessages.needToLogin, output);
//    }
    @Test
    public void canNotDo() throws IOException{
        var commands = Arrays.asList("play", "score", "me");
        List<String> output;
        for (var command : commands){
            output = bot.processInput("/help", 0);
            assertEquals(BotMessages.needToLogin, output.get(0));
        }
    }
}
