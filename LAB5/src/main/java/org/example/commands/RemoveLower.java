package org.example.commands;

import org.example.collection.*;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

import java.util.List;

public class RemoveLower implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;
    private final PersonIOService personIOService;

    public RemoveLower(CollectionManager collectionManager, IOService ioService, PersonIOService personIOService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
        this.personIOService = personIOService;
    }

    @Override
    public void execute() {
        try {
            Person threshold = personIOService.readPerson();
            List<Person> removedPersons = collectionManager.removeLower(threshold);
            ioService.print("Removed " + removedPersons.size() + " persons");
        } catch (Exception e) {
            ioService.print("Error: " + e.getMessage());
        }
    }
}
