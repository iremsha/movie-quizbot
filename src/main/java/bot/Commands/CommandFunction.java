package bot.Commands;

import bot.Bot.*;

import java.io.IOException;

@FunctionalInterface
public interface CommandFunction {
    String commandFunction(Bot bot, String input, int sessionId) throws IOException;
}