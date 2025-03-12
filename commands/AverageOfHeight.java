package org.example.commands;

import org.example.utils.CollectionManager;

public class AverageOfHeight implements Commands {
    private final CollectionManager collectionManager;

    public AverageOfHeight(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.printAverageHeight();
    }

    @Override
    public void execute(String arg) {

    }
}