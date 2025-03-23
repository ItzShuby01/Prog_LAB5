package org.example.commands;

import org.example.utils.CollectionManager;
import java.util.LinkedList;

public class History implements Command {
    private final LinkedList<String> commandHistory;

    public History(LinkedList<String> commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Override
    public void execute() {
        System.out.println("Last " + commandHistory.size() + " commands:");
        for (String command : commandHistory) {
            System.out.println(command);
        }
    }

}
