package org.example.commands;

import org.example.collection.*;
import org.example.utils.*;
import java.time.LocalDateTime;

public class Add implements Command {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        // 1) Interactive mode: prompt user for input
        collectionManager.addPerson();
    }

    @Override
    public void execute(String arg) {
        if (arg == null || arg.isEmpty()) {
            execute(); // Fallback to interactive mode
            return;
        }

        // 2) Script mode: parse and read  arguments from file
        String[] parts = arg.split(",");
        if (parts.length != 10) {
            System.out.println("Error: Expected 10 fields for 'add' command.");
            return;
        }

        try {
            String name = parts[0].trim();
            double x = Double.parseDouble(parts[1].trim());
            double y = Double.parseDouble(parts[2].trim());
            double height = Double.parseDouble(parts[3].trim());
            EyeColor eyeColor = EyeColor.valueOf(parts[4].trim().toUpperCase());
            HairColor hairColor = HairColor.valueOf(parts[5].trim().toUpperCase());
            Country nationality = Country.valueOf(parts[6].trim().toUpperCase());
            double locationX = Double.parseDouble(parts[7].trim());
            double locationY = Double.parseDouble(parts[8].trim());
            String locationName = parts[9].trim();

            Coordinates coordinates = new Coordinates((int)x, y);
            Location location = new Location((float) locationX, (float)locationY, locationName);

            Person person = new Person(
                    collectionManager.generateId(),
                    name,
                    coordinates,
                    LocalDateTime.now(),
                    height,
                    eyeColor,
                    hairColor,
                    nationality,
                    location
            );

            collectionManager.add(person);
            System.out.println("Added: " + person.getName());
        } catch (Exception e) {
            System.out.println("Error parsing 'add' command: " + e.getMessage());
        }
    }
}
