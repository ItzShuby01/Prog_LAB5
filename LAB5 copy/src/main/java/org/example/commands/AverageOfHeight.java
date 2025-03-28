package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class AverageOfHeight implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;

    public AverageOfHeight(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        try {
            double average = collectionManager.getAverageHeight();
            ioService.print(String.format("Average height: %.2f", average));
        } catch (IllegalStateException e) {
            ioService.print(e.getMessage());
        }
    }

}