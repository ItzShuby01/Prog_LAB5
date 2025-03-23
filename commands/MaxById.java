package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.collection.Person;

public class MaxById implements Command {
    private final CollectionManager collectionManager;

    public MaxById(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        Person maxPerson = collectionManager.getMaxById();
        if (maxPerson != null) {
            System.out.println("Person with maximum ID:\n" + maxPerson);
        } else {
            System.out.println("Collection is empty.");
        }
    }

}
