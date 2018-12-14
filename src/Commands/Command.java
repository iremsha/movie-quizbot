package Commands;

public class Command {
    public String Name;
//    public boolean isPriority;
    public String Description;
    public CommandFunction Execute;

    public Command(String name, String description, CommandFunction commandFunction){
        this.Name = name;
        this.Description = description;
        this.Execute = commandFunction;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Commands.Command && ((Commands.Command) obj).Name == this.Name;
//    }
}


