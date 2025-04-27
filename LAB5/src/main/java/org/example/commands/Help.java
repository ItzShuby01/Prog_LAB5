package org.example.commands;

import org.example.utils.CommandManager;
import org.example.utils.IOService;

public class Help implements Command {
    private final CommandManager commandManager;
    private final IOService ioService; // Add IOService for consistent output
    public static final String DESCRIPTION = "help: display help on available commands";

    public Help(CommandManager commandManager, IOService ioService) {
        this.commandManager = commandManager;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        // Get descriptions from command instances via CommandManager
        commandManager.getCommandDescriptions().values().forEach(ioService::print);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}