package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class RemoveById implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;
    public static final String DESCRIPTION = "remove_by_id id: remove an element from a collection by its id";



    public RemoveById(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute(String arg) {
        String trimmedArg = arg.trim();

        if (!trimmedArg.matches("[1-9]\\d*")) { // Allow only positive integers
            ioService.print("Invalid ID format: must be a positive integer");
            return;
        }

        try {
            int id = Integer.parseInt(trimmedArg);
            Person person = collectionManager.getById(id);

            if (person != null) {
                collectionManager.removePerson(person);
                ioService.print("Person with ID " + id + " removed");
            } else {
                ioService.print("ID not found");
            }
        } catch (NumberFormatException e) {
            ioService.print("Invalid ID format: " + e.getMessage());
        }
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
