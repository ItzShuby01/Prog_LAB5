package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;

import java.util.List;

public class RemoveLower implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;


    public RemoveLower(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute(String arg) {
        try {
            int id = Integer.parseInt(arg);
            List<Person> removedPersons = collectionManager.removeLower(id);

            if (removedPersons.isEmpty()) {
                ioService.print("No Persons removed. Either ID was not found or the given ID is the lowest.");
            } else {
                ioService.print("Removed " + removedPersons.size() + " elements lower than ID " + id);
            }
        } catch (NumberFormatException e) {
            ioService.print("Invalid ID. Please enter an integer." + e.getMessage());
        }
    }
}
