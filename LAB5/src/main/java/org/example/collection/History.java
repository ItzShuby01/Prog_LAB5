package org.example.commands;

import org.example.utils.IOService;

import java.util.LinkedList;

public class History implements Command {
    private final LinkedList<String> commandHistory;
    private final IOService ioService;

    public History(LinkedList<String> commandHistory, IOService ioService) {
        this.commandHistory = commandHistory;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        ioService.print("Last " + commandHistory.size() + " commands:");
        for (String command : commandHistory) {
            ioService.print(command);
        }
    }
}
