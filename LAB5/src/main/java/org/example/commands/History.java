package org.example.commands;

import org.example.utils.IOService;

import java.util.LinkedList;

public class History implements Command {
    private final LinkedList<String> commandHistory;
    private final IOService ioService;
    public static final String DESCRIPTION = "history: print the last 7 commands (without their arguments)";

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
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
