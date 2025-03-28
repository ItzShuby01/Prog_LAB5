package org.example.commands;

import org.example.collection.*;
import org.example.utils.*;
import java.time.LocalDateTime;

public class Add implements Command {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;

    public Add(CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        // 1) interactive mode
        Person person = personIOService.readPerson();
        boolean added = collectionManager.add(person);
        if (added) {
            ioService.print(person.getName() + " added to collection");
        } else {
            ioService.print("Failed to add person");
        }
    }

    @Override
    public void execute(String arg) {
        if (arg == null || arg.isEmpty()) {
            execute();
            return;
        }

        // 2) Script mode
        String[] parts = arg.split("\\n");
        if (parts.length != 10) {
            ioService.print("Error: Need exactly 10 lines of data");
            return;
        }

        try {
            // Parse the fields...
            String name = parts[0].trim();
            double x = Double.parseDouble(parts[1].trim());
            double y = Double.parseDouble(parts[2].trim());
            double height = Double.parseDouble(parts[3].trim());

            // EyeColor Validation
            String eyeColorStr = parts[4].trim().toUpperCase();
            EyeColor eyeColor;
            switch (eyeColorStr) {
                case "RED":
                    eyeColor = EyeColor.RED;
                    break;
                case "BLACK":
                    eyeColor = EyeColor.BLACK;
                    break;
                case "ORANGE":
                    eyeColor = EyeColor.ORANGE;
                    break;
                default:
                    ioService.print("Invalid eye color: '" + eyeColorStr + "'. Valid options: RED, BLACK, ORANGE");
                    return;
            }

            //  HairColor validation
            String hairColorStr = parts[5].trim().toUpperCase();
            HairColor hairColor;
            switch (hairColorStr) {
                case "GREEN":
                    hairColor = HairColor.GREEN;
                    break;
                case "BLUE":
                    hairColor = HairColor.BLUE;
                    break;
                case "YELLOW":
                    hairColor = HairColor.YELLOW;
                    break;
                case "BROWN":
                    hairColor = HairColor.BROWN;
                    break;
                default:
                    ioService.print("Invalid hair color: '" + hairColorStr + "'. Valid options: GREEN, BLUE, YELLOW, BROWN");
                    return;
            }

            // Nationality Validation
            String countryStr = parts[6].trim().toUpperCase();
            Country nationality;
            switch (countryStr) {
                case "RUSSIA":
                    nationality = Country.RUSSIA;
                    break;
                case "GERMANY":
                    nationality = Country.GERMANY;
                    break;
                case "ITALY":
                    nationality = Country.ITALY;
                    break;
                case "THAILAND":
                    nationality = Country.THAILAND;
                    break;
                case "JAPAN":
                    nationality = Country.JAPAN;
                    break;
                default:
                    ioService.print("Invalid country: '" + countryStr + "'. Valid options: RUSSIA, GERMANY, ITALY, THAILAND, JAPAN.");                    return;
            }

            // ...Parse remaining fields
            double locationX = Double.parseDouble(parts[7].trim());
            double locationY = Double.parseDouble(parts[8].trim());
            String locationName = parts[9].trim();


            Coordinates coordinates = new Coordinates((int) x, y);
            Location location = new Location((float) locationX, (float) locationY, locationName);

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
            ioService.print("Added: " + person.getName());

        } catch (NumberFormatException e) {
            ioService.print("Invalid number format in input");
        } catch (Exception e) {
            ioService.print("Error: " + e.getMessage());
        }
    }
}