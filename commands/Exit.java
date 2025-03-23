package org.example.commands;

import org.example.utils.ConsoleManager;

public class Exit implements Command {
    private final ConsoleManager consoleManager;

    public Exit(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    @Override
    public void execute() {
        System.out.println("Exiting the app. See you again later!!!");
        consoleManager.stop();
    }
}
