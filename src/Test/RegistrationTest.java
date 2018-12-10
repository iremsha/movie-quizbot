package Test;
import Bot.Bot;
import Bot.BotMessages;
import Bot.QuizBot;
import User.UserManagerForTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {
    Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());

    private RegistrationTest() throws IOException {
//        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
    }

    @Test
    public void canCreateUser() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        var output = bot.processInput("login password", 0);
        var user = bot.sessions.get(0).user;
        assertNotNull(user);
        assertEquals("login", user.Login);
    }
    @Test
    public void canNotCreateWithTakenLogin() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
        bot.processInput("/create", 0);
        var output = bot.processInput("login password", 0);
        assertEquals(BotMessages.loginHasTaken, output.get(0));
    }
    @Test
    public void canLogin() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
        bot.processInput("/exit", 0);
        bot.processInput("/login", 0);
        bot.processInput("login password", 0);
        var user = bot.sessions.get(0).user;
        assertNotNull(user);
        assertEquals("login", user.Login);
    }
    @Test
    public void canLogout() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
        bot.processInput("/exit", 0);
        var user = bot.sessions.get(0).user;
        assertNull(user);
    }

    @Test
    public void canNotLoginWithNewLogin() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/login", 0);
        var output = bot.processInput("login password", 0);
        var user = bot.sessions.get(0).user;
        assertNull(user);
        assertEquals(BotMessages.noUserWithThisLogin, output.get(0));
    }
    @Test
    public void canNotLoginWithWrongPassword() throws IOException {
        Bot bot = new Bot(new QuizBot(null), UserManagerForTest.getInstance());
        bot.processInput("/create", 0);
        bot.processInput("login password", 0);
        bot.processInput("/exit", 0);
        bot.processInput("/login", 0);
        var output = bot.processInput("login wrong_password", 0);
        assertEquals(BotMessages.incorrectPassword, output.get(0));
        var user = bot.sessions.get(0).user;
        assertNull(user);
    }

}
