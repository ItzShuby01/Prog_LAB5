package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.collection.Person;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

public class AddIfMax implements Command {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;

    public AddIfMax(CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        ioService.print("Creating a new person to check if it's the max by height...");
        Person newPerson = personIOService.readPerson();
        double currentMaxHeight = collectionManager.getMaxHeight();

        if (newPerson.getHeight() > currentMaxHeight) {
            collectionManager.add(newPerson);
            ioService.print("New person added to collection as their height is greater than the current maximum.");
        } else {
            ioService.print("New person's height is not greater than the current maximum. Not added to collection.");
        }
    }
}
