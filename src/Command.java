import java.io.IOException;

public class Command {
    public String Name;
//    public boolean isPriority;
    public String Description;
////    public Function<>
    public CommandFunction Execute;

    public Command(String name, String description, CommandFunction commandFunction){
        this.Name = name;
        this.Description = description;
        this.Execute = commandFunction;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Command && ((Command) obj).Name == this.Name;
//    }
}

@FunctionalInterface
interface CommandFunction{
    String commandFunction(Bot bot, String input, Session session) throws IOException;
}

