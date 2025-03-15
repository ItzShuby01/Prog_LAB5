package org.example.commands;

import org.example.utils.CollectionManager;

public class Clear implements Command{
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.clear();
    }

    @Override
    public void execute(String arg) {

    }
}
