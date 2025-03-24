package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class RemoveById implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;

    public RemoveById(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute(String arg) {
        try{
            int id = Integer.parseInt(arg);
            boolean idFound = false;

            for(Person person : collectionManager.getPersonTreeSet()) {
                if(person.getId() == id) {
                    idFound = true;
                    collectionManager.removePerson(person);
                    ioService.print("Person with " + id + " has been removed");
                    break;
                }
            }

            if(!idFound) {
                ioService.print("Id not found in collection");
            }
        } catch (Exception e){
            ioService.print("Invalid Id format");
        }
    }
}
