package Test;

import Bot.Bot;
import Bot.BotMessages;
import Bot.QuizBot;
import User.UserManagerForTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class FriendsTest {
    //Bot bot;
    private FriendsTest() throws IOException {

    }
    private Bot createBotWithUsers(List<String> names) throws IOException {
        var bot = new Bot(new QuizBot(null), UserManagerForTest.getNewInstance());
        for (var name : names){
            bot.processInput("/create", 0);
            bot.processInput(name + " " + name, 0);
        }
        bot.processInput("/exit", 0);
        return bot;
    }

    @Test
    public void canAddFriend() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1", "t2"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        bot.processInput("/add t2", 0);
        assertTrue(bot.userManager.getUser("t1").Friends.contains("t2"));
    }
    @Test
    public void canNotAddIfUserDoesNotExist() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        var output = bot.processInput("/add z", 0);
        assertEquals(BotMessages.noUserWithThisLogin, output.get(0));
    }
    @Test
    public void canNotAddHimSelf() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("a"));
        bot.processInput("/login", 0);
        bot.processInput("a a", 0);
        var output = bot.processInput("/add a", 0);
        assertEquals(BotMessages.cantAddYourSelf, output.get(0));
    }
    @Test
    public void areFriendsIfBothAdd() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1", "t2"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        bot.processInput("/add t2", 0);
        bot.processInput("/login", 0);
        bot.processInput("t2 t2", 0);
        bot.processInput("/add t1", 0);
        var output = bot.processInput("/friends", 0);
        assertEquals("[t1]", output.get(0));
    }
    @Test
    public void areNotFriendsIfOneAdd() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1", "t2"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        bot.processInput("/add t2", 0);
        var output = bot.processInput("/friends", 0);
        assertEquals("[]", output.get(0));
    }
    @Test
    public void canGetFriendInfo() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1", "t2"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        bot.processInput("/add t2", 0);
        bot.processInput("/login", 0);
        bot.processInput("t2 t2", 0);
        bot.processInput("/add t1", 0);
        var output = bot.processInput("/score t1", 0);
        assertEquals("0", output.get(0));
    }
    @Test
    public void canNotGetNotFriendInfo() throws IOException {
        var bot = createBotWithUsers(Arrays.asList("t1", "t2"));
        bot.processInput("/login", 0);
        bot.processInput("t1 t1", 0);
        var output = bot.processInput("/score t2", 0);
        assertEquals(BotMessages.canSeeOnlyFriendsInfo, output.get(0));
    }
}
