package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.collection.Person;
import java.util.Scanner;

public class AddIfMax implements Commands {
    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new person to check if it's the max by height...");
        Person newPerson = collectionManager.readPersonFromScanner(scanner);
        double currentMaxHeight = collectionManager.getMaxHeight();

        if (newPerson.getHeight() > currentMaxHeight) {
            collectionManager.add(newPerson);
            System.out.println("New person added to collection as their height is greater than the current maximum.");
        } else {
            System.out.println("New person's height is not greater than the current maximum. Not added to collection.");
        }
    }

    @Override
    public void execute(String arg) {}
}