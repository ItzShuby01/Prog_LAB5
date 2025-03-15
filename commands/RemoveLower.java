package org.example.commands;

import org.example.utils.CollectionManager;

public class RemoveLower implements Command {
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(String arg) {
        collectionManager.removeLower(arg);
    }
}
