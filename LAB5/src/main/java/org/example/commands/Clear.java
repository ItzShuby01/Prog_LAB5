package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class Clear implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;

    public Clear(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        collectionManager.clear();
        ioService.print("Collection has been cleared successfully");
    }
}
