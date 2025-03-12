package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;

public class Update implements Commands{
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(String arg) {
        try{
            int id = Integer.parseInt(arg);
            boolean idFound = false;

            for(Person person : collectionManager.getPersonTreeSet()) {
                if(person.getId() == id) {
                    idFound = true;
                    collectionManager.updatePerson(person);
                    break;
                }
            }

            if(!idFound) {
                System.out.println("Id not found in collection");
            }
        } catch (Exception e){
            System.out.println("Invalid Id format");
        }


    }
}
