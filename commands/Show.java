package org.example.commands;

import org.example.utils.CollectionManager;

public class Show implements Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.show();
    }

    @Override
    public void execute(String arg) {

    }
}
