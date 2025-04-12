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


    //Getter method and also Helper method for 'SHOW'
    public TreeSet<Person> getPersonTreeSet() {
        return personTreeSet;
    }

    // Set the collection
    public void setPersonTreeSet(TreeSet<Person> loadedCollection) {
        if (loadedCollection != null) {
            personTreeSet.clear();
            personTreeSet.addAll(loadedCollection);
        }
    }

    // Retrieve all 'Person' objects as a list
    public List<Person> getAllPersons() {
        return new ArrayList<>(personTreeSet);
    }

    //Automatically generates ID
    public int generateId(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE) + 1;
        for (Person person : personTreeSet) {
            while (person.getId() == id) {
                id = random.nextInt(Integer.MAX_VALUE) + 1;
            }
        }
        return id;
    }

    //Method for adding person to the collection
    public boolean add(Person person) {
        return personTreeSet.add(person);
    }

    //Helper method for 'REMOVE_BY_ID'
    public void removePerson(Person person) {
        personTreeSet.remove(person);
    }


    // Helper method for 'CLEAR'
    public void clear() {
        ArrayList<Person> personList = new ArrayList<>(personTreeSet);
        personList.forEach(personTreeSet::remove);
    }

    // Get the person with the maximum ID
    public Person getMaxById() {
        return personTreeSet.isEmpty() ? null : personTreeSet.last();
    }

    public double getAverageHeight() {
        if (personTreeSet.isEmpty()) {
            throw new IllegalStateException("Collection is empty");
        }
        return personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .average()
                .orElse(0.0);
    }
    // Helper for 'COUNT_BY_LOCATION'
    public int countByLocation(String locationName) {
        int count = 0;
        for (Person person : personTreeSet) {
            if (person.getLocation().getName().equalsIgnoreCase(locationName)) {
                count++;
            }
        }
        return count;
    }
    public Person getById(int id) {
        for (Person person : personTreeSet) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public static class InfoData {
        private final String collectionType;
        private final String initializationDate;
        private final int elementCount;

        public InfoData(String collectionType, String initializationDate, int elementCount) {
            this.collectionType = collectionType;
            this.initializationDate = initializationDate;
            this.elementCount = elementCount;
        }

        // Getters
        public String getCollectionType() { return collectionType; }
        public String getInitializationDate() { return initializationDate; }
        public int getElementCount() { return elementCount; }
    }

    public InfoData getInfo() {
        return new InfoData(
                personTreeSet.getClass().getName(),
                getInitializationDate(),
                personTreeSet.size()
        );
    }


    // Helper for 'INFO' to get the initialization date from the storage file
    private String getInitializationDate() {
        String date = " ";
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
            throw new RuntimeException("Failed to read file " + e.getMessage());
        }
        return date;
    }

    // Helper for 'REMOVE_LOWER'
    public List<Person> removeLower(int inputtedId) {
        List<Person> toRemove = new ArrayList<>();
        Person referencePerson = null;

        // Find the reference person
        for (Person person : personTreeSet) {
            if (person.getId() == inputtedId) {
                referencePerson = person;
                break;
            }
        }

        if (referencePerson == null) {
            return List.of(); // No person found with the inputted ID
        }

        // Collect persons to remove
        for (Person person : personTreeSet) {
            if (referencePerson.getId() > person.getId()) {
                toRemove.add(person);
            }
        }

        // Remove collected persons
         toRemove.forEach(personTreeSet::remove);
        return toRemove; // Return the removed persons
    }


    // Get the maximum height of persons in the collection
    public double getMaxHeight() {
        return personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .max()
                .orElse(0.0);
    }
}
