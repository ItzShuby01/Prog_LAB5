package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class CountByLocation implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;

    public CountByLocation(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute(String arg) {
        int count = collectionManager.countByLocation(arg);
        ioService.print("Number of persons with location '" + arg.toUpperCase() + "' is " + count);
    }
}
