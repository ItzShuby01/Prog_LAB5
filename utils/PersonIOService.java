package org.example.utils;

import org.example.collection.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//This class solely deals with user I/O
public class PersonIOService {
    private final IOService ioService;
    private final CollectionManager collectionManager;

    public PersonIOService(IOService ioService, CollectionManager collectionManager) {
        this.ioService = ioService;
        this.collectionManager = collectionManager;
    }

    public Person readPerson() {
        int id = collectionManager.generateId();
        ioService.print("ID automatically generated: " + id);

        String name = ioService.readLine("Enter person name: ");
        Coordinates coordinates = readCoordinate();
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
        ioService.print("Creation date was automatically generated: " + creationDate.format(formatter));
        double height = readHeight();
        EyeColor eyeColor = readEyeColor();
        HairColor hairColor = readHairColor();
        Country nationality = readCountry();
        Location location =readLocation();

        return new Person(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
    }

    private Coordinates readCoordinate() {
        Coordinates coordinates = new Coordinates();
        boolean validInput = false;
        while (!validInput) {
            try {
                ioService.print("Enter person coordinates");
                int x = ioService.readInt("x: ");
                if (x > 629) {
                    throw new Exception("x exceeds limits");
                }
                double y = ioService.readDouble("y: ");
                coordinates.setX(x);
                coordinates.setY(y);
                validInput = true;
            } catch (Exception e) {
                ioService.print(e.getMessage());
                ioService.print("Invalid formats. x must be an integer less than 630 and y a double.");
            }
        }
        return coordinates;
    }

    private double readHeight() {
        return ioService.readDouble("Enter person height: ");
    }

    private EyeColor readEyeColor() {
        while (true) {
            try {
                String input = ioService.readLine("Enter person eye color: ").toUpperCase();
                return EyeColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid eye colors are RED, BLACK, and ORANGE.");
            }
        }
    }

    private HairColor readHairColor() {
        while (true) {
            try {
                String input = ioService.readLine("Enter person hair color: ").toUpperCase();
                return HairColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid hair colors are GREEN, BLUE, YELLOW, and BROWN.");
            }
        }
    }

    private Country readCountry() {
        while (true) {
            try {
                String input = ioService.readLine("Enter nationality: ").toUpperCase();
                return Country.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid options are RUSSIA, GERMANY, ITALY, THAILAND, and JAPAN.");
            }
        }
    }

    private Location readLocation() {
        Location location = new Location();
        boolean validInput = false;
        while (!validInput) {
            try {
                ioService.print("Enter person location");
                float x = (float) ioService.readDouble("x: ");
                float y = (float) ioService.readDouble("y: ");
                String name = ioService.readLine("name: ");
                if (name.length() > 530) {
                    throw new Exception("Length of name too long");
                }
                location.setX(x);
                location.setY(y);
                location.setName(name);
                validInput = true;
            } catch (Exception e) {
                ioService.print("Invalid formats. 'x' and 'y' must be floats and 'name' a string.");
            }
        }
        return location;
    }

    public void updatePerson(Person person) {
        String name = ioService.readLine("Enter name: ");
        Coordinates coordinates = readCoordinate();
        ioService.print("Creation date cannot be changed");
        double height = readHeight();
        EyeColor eyeColor = readEyeColor();
        HairColor hairColor = readHairColor();
        Country nationality = readCountry();
        Location location = readLocation();

        person.setName(name);
        person.setCoordinates(coordinates);
        person.setHeight(height);
        person.setEyeColor(eyeColor);
        person.setHairColor(hairColor);
        person.setNationality(nationality);
        person.setLocation(location);

        ioService.print("Person with id " + person.getId() + " has been updated");
    }

}
