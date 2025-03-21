package org.example.commands;

import org.example.utils.CommandManager;

public class Help implements Command {
    private final CommandManager commandManager;

    // Accept CommandManager in the constructor
    public Help(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        // Dynamically generate help message from CommandManager's descriptions
        StringBuilder manual = new StringBuilder();
        commandManager.getCommandDescriptionMap().forEach((name, description) ->
                manual.append(description).append("\n")
        );
        System.out.println(manual);
    }

    @Override
    public void execute(String arg) {
    }
}
