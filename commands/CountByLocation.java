package org.example.commands;

import org.example.utils.CollectionManager;

public class CountByLocation implements Command{
    private final CollectionManager collectionManager;
    public CountByLocation(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(String arg) {
        int count = collectionManager.countByLocation(arg);
        System.out.println("Number of persons with location " + arg.toLowerCase() + " is " + count);
    }
}
