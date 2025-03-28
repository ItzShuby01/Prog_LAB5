package org.example.commands;

import org.example.utils.CommandManager;
import org.example.utils.IOService;

public class Exit implements Command {
    private final CommandManager  commandManager;
    private final IOService ioService;

    public Exit(CommandManager commandManager, IOService ioService) {
        this.commandManager = commandManager;
        this.ioService =ioService;
    }

    @Override
    public void execute() {
        ioService.print("Exiting the App. See You Again Later!!!");
        commandManager.requestExit();
    }
}