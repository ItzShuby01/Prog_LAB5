package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.collection.*;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

import java.time.LocalDateTime;


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
        // 1) Interactive mode
        ioService.print("Creating a new person to check if it's the max by height...");
        Person newPerson = personIOService.readPerson();
        checkAndAdd(newPerson);
    }

    @Override
    public void execute(String arg) {
        // 2)  Script mode
        if (arg == null || arg.isEmpty()) {
            execute(); // Fallback to interactive mode
            return;
        }

        String[] parts = arg.split("\\n");
        if (parts.length != 10) {
            ioService.print("Error: Need exactly 10 lines of data");
            return;
        }

        try {
            // Validate all parameters
            for (int i = 0; i < 10; i++) {
                if (!Add.inputValidation(i, parts[i], ioService)) {
                    ioService.print("Validation failed for parameter " + Add.INPUTS_LABELS[i]);
                    return;
                }
            }

            // Parse inputs into a Person object
            Person person = parsePerson(parts);
            checkAndAdd(person);

        } catch (NumberFormatException e) {
            ioService.print("Invalid number format");
        } catch (IllegalArgumentException e) {
            ioService.print("Invalid value: " + e.getMessage());
        }
    }

    private Person parsePerson(String[] parts) {
        return new Person(
                collectionManager.generateId(),
                parts[0].trim(), // name
                new Coordinates(
                        (int) Double.parseDouble(parts[1]), // x
                        Double.parseDouble(parts[2])       // y
                ),
                LocalDateTime.now(),
                Double.parseDouble(parts[3]),          // height
                EyeColor.valueOf(parts[4].trim().toUpperCase()), // eyeColor
                HairColor.valueOf(parts[5].trim().toUpperCase()), // hairColor
                Country.valueOf(parts[6].trim().toUpperCase()), // nationality
                new Location(
                        Float.parseFloat(parts[7]),       // locationX
                        Float.parseFloat(parts[8]),       // locationY
                        parts[9].trim()                   // locationName
                )
        );
    }

    private void checkAndAdd(Person newPerson) {
        double currentMaxHeight = collectionManager.getMaxHeight();
        if (newPerson.getHeight() > currentMaxHeight) {
            collectionManager.add(newPerson);
            ioService.print("ADDED: " + newPerson.getName() + " as his height: " + "(" + newPerson.getHeight() + ")" + " > " +  "max height (" + currentMaxHeight + ")");
        } else {
            ioService.print( newPerson.getName() + " NOT ADDED as his height: " + "(" + newPerson.getHeight() + ")" + " < " +  "max height (" + currentMaxHeight + ")");
        }
    }
}
