package org.example.commands;

import org.example.commands.Commands;
import org.example.utils.ConsoleManager;

public class Exit implements Commands {
    private final ConsoleManager consoleManager;

    public Exit(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    @Override
    public void execute() {
        System.out.println("Exiting the app. See you later!");
        consoleManager.stop();
    }

    @Override
    public void execute(String arg) {
        execute();
    }
}
