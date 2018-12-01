package Commands;

import Bot.*;


import java.io.IOException;

@FunctionalInterface
public interface CommandFunction{
    String commandFunction(Bot bot, String input, Session session) throws IOException;
}