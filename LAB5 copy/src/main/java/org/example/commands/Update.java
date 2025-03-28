package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

public class Update implements Command {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;

    public Update(CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
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
                    personIOService.updatePerson(person);
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
