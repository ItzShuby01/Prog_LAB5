package org.example.commands;

import org.example.utils.ConsoleManager;
import org.example.utils.IOService;

public class Exit implements Command {
    private final ConsoleManager consoleManager;
    private final IOService ioService;

    public Exit(ConsoleManager consoleManager, IOService ioService) {
        this.consoleManager = consoleManager;
        this.ioService =ioService;
    }

    @Override
    public void execute() {
        ioService.print("Exiting the App. See You Again Later!!!");
        consoleManager.stop();
    }
}
