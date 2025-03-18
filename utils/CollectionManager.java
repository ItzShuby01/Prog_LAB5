package org.example.utils;


import org.example.collection.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class CollectionManager {
    private final TreeSet<Person> personTreeSet = new TreeSet<>();


    // Retrieve all 'Person' objects as a list
    public List<Person> getAllPersons() {
        return new ArrayList<>(personTreeSet);
    }



    private final IOService ioService;
    // Constructor injection of IOService
    public CollectionManager(ConsoleIOService ioService) {
        this.ioService = ioService;
    }

    public TreeSet<Person> getPersonTreeSet() {
        return personTreeSet;
    }

    public void setPersonTreeSet(TreeSet<Person> loadedCollection) {
        if (loadedCollection != null) {
            personTreeSet.clear();
            personTreeSet.addAll(loadedCollection);
        } else {
            ioService.print("Warning: Attempted to load null collection");
        }
    }

    // Read person details using IOService (for command 'add' or 'add_if_max')
    public Person readPerson() {
        int id = generateId();
        ioService.print("ID automatically generated: " + id);

        String name = ioService.readLine("Enter person name: ");
        Coordinates coordinates = addCoordinate();
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
        ioService.print("Creation date was automatically generated: " + creationDate.format(formatter));
        double height = addHeight();
        EyeColor eyeColor = addEyeColor();
        HairColor hairColor = addHairColor();
        Country nationality = addCountry();
        Location location = addLocation();

        return new Person(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
    }

    // Helper methods used by readPerson() and other commands:
    private int generateId(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE) + 1;
        for (Person person : personTreeSet) {
            while (person.getId() == id) {
                id = random.nextInt(Integer.MAX_VALUE) + 1;
            }
        }
        return id;
    }

    private Coordinates addCoordinate() {
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

    private double addHeight(){
        return ioService.readDouble("Enter person height: ");
    }

    private EyeColor addEyeColor() {
        while (true) {
            try {
                String input = ioService.readLine("Enter person eye color: ").toUpperCase();
                return EyeColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid eye colors are RED, BLACK, and ORANGE.");
            }
        }
    }

    private HairColor addHairColor() {
        while (true) {
            try {
                String input = ioService.readLine("Enter person hair color: ").toUpperCase();
                return HairColor.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid hair colors are GREEN, BLUE, YELLOW, and BROWN.");
            }
        }
    }

    private Country addCountry() {
        while (true) {
            try {
                String input = ioService.readLine("Enter nationality: ").toUpperCase();
                return Country.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid options are RUSSIA, GERMANY, ITALY, THAILAND, and JAPAN.");
            }
        }
    }

    private Location addLocation() {
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
                ioService.print("Invalid formats. x and y must be floats and name a string.");
            }
        }
        return location;
    }

    // Helper method for adding a person to the collection (for 'add' and 'add_if_max' commands)
    public void add(Person person) {
        personTreeSet.add(person);
        ioService.print(person.getName() + " has been added to the collection");
    }

    // Command method for 'ADD'
    public void addPerson() {
        Person newPerson = readPerson();
        add(newPerson);
    }

    // Command method for 'UPDATE'
    public void updatePerson(Person person) {
        String name = ioService.readLine("Enter name: ");
        Coordinates coordinates = addCoordinate();
        ioService.print("Creation date cannot be changed");
        double height = addHeight();
        EyeColor eyeColor = addEyeColor();
        HairColor hairColor = addHairColor();
        Country nationality = addCountry();
        Location location = addLocation();

        person.setName(name);
        person.setCoordinates(coordinates);
        person.setHeight(height);
        person.setEyeColor(eyeColor);
        person.setHairColor(hairColor);
        person.setNationality(nationality);
        person.setLocation(location);

        ioService.print("Person with id " + person.getId() + " has been updated");
    }

    // Command method for 'REMOVE_BY_ID'
    public void removePerson(Person person) {
        personTreeSet.remove(person);
    }

    // Command method for 'CLEAR'
    public void clear() {
        ArrayList<Person> personList = new ArrayList<>(personTreeSet);
        personList.forEach(personTreeSet::remove);
        ioService.print("Collection has been cleared");
    }

    // Command method for 'SHOW'
    public void show() {
        if (personTreeSet.isEmpty()) {
            ioService.print("THE COLLECTION IS EMPTY");
        } else {
            ioService.print("DISPLAYING THE COLLECTION DATA");
            for (Person person : personTreeSet) {
                ioService.print(person.toString());
                ioService.print(""); // Print an empty line for spacing
            }
        }
    }

    // Command method for 'INFO'
    public void info() {
        ioService.print("Collection type: " + personTreeSet.getClass());
        String initializationDate = getInitializationDate();
        ioService.print("Initialization Date: " + initializationDate);
        int elementsCount = personTreeSet.size();
        ioService.print("Number of persons: " + elementsCount);
    }

    // Helper for 'INFO' to get the initialization date from the storage file
    private String getInitializationDate() {
        String date = "";
        String path = System.getenv("COLLECTION_FILE_PATH");
        if (path == null) {
            return "Initialization date not available because the environmental variable is not set";
        }
        try {
            BasicFileAttributes attributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
            FileTime fileTime = attributes.creationTime();
            Instant instant = fileTime.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
            date = localDateTime.format(formatter);
        } catch (IOException e) {
            ioService.print("EXCEPTION: " + e.getMessage());
        }
        return date;
    }

    // Command method for 'MAX_BY_ID'
    public Person getMaxById() {
        return personTreeSet.isEmpty() ? null : personTreeSet.last();
    }

    // Command method for 'AVERAGE_OF_HEIGHT'
    public void printAverageHeight() {
        if (personTreeSet.isEmpty()) {
            ioService.print("Collection is empty");
            return;
        }
        double averageHeight = personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .average()
                .orElse(0.0);
        ioService.print(String.format("Average height: %.2f", averageHeight));
    }

    // Command method for 'COUNT_BY_LOCATION'
    public int countByLocation(String locationName) {
        int count = 0;
        for (Person person : personTreeSet) {
            if (person.getLocation().getName().equalsIgnoreCase(locationName)) {
                count++;
            }
        }
        return count;
    }

    // Command method for 'REMOVE_LOWER'
    public void removeLower(String idString) {
        try {
            int inputtedId = Integer.parseInt(idString);
            ArrayList<Person> toRemove = new ArrayList<>();
            Person personWithId = null;
            int position = 0;
            for (Person person : personTreeSet) {
                position++;
                if (person.getId() == inputtedId) {
                    personWithId = person;
                    break;
                }
            }
            ioService.print("Position: " + position);
            if (personWithId == null) {
                ioService.print("No person in collection exists with ID " + inputtedId);
                return;
            }
            for (Person person : personTreeSet) {
                if (personWithId.getId() > person.getId()) {
                    toRemove.add(person);
                }
            }
            if (toRemove.isEmpty()) {
                ioService.print("Person with id " + inputtedId + " is the lowest");
            } else {
                toRemove.forEach(personTreeSet::remove);
                ioService.print("Removed all elements lower");
            }
        } catch (RuntimeException e) {
            ioService.print(e.getMessage());
            ioService.print("ID must be an integer");
        }
    }

    // Command method for 'GET_MAX_HEIGHT'
    public double getMaxHeight() {
        return personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .max()
                .orElse(0.0);
    }
}
