package org.example.utils;



import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.collection.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

@XmlRootElement(name = "sorted-set")
@XmlAccessorType(XmlAccessType.FIELD)
public class CollectionManager {
    @XmlElement(name = "person")
    private final TreeSet<Person> personTreeSet = new TreeSet<>();

    public TreeSet<Person> getPersonTreeSet() {
        return personTreeSet;
    }

    public void setPersonTreeSet(TreeSet<Person> loadedCollection) {
        if(loadedCollection != null) {
            personTreeSet.clear();
            personTreeSet.addAll(loadedCollection);
        } else {
            System.out.println("Warning: Attempted to load null collection");
        }
    }


    //Read person details from scanner without adding to collection
    public Person readPersonFromScanner(Scanner scanner) {
        int id = generateId();
        System.out.println("ID automatically generated: " + id);

        System.out.print("Enter person name: ");
        String name = scanner.next();

        Coordinates coordinates = addCoordinate(scanner);

        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
        String formattedDate = creationDate.format(formatter);
        System.out.println("Creation date was automatically generated: " + formattedDate);

        double height = addHeight(scanner);
        EyeColor eyeColor = addEyeColor(scanner);
        HairColor hairColor = addHairColor(scanner);
        Country nationality = addCountry(scanner);
        Location location = addLocation(scanner);

        return new Person(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
    }


    //Methods to get the details of the 'Person'
    private int generateId(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE) + 1;
        for(Person person : personTreeSet) {
            while (person.getId() == id) {
                id = random.nextInt(Integer.MAX_VALUE) + 1;
            }
        }
        return id;
    }

    private Coordinates addCoordinate(Scanner scanner) {
        Coordinates coordinates = new Coordinates();
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter person coordinates");
                System.out.print("x: ");
                int x = scanner.nextInt();
                if(x > 629) {
                    throw new Exception("x exceed limits");
                }

                System.out.print("y: ");
                double y = scanner.nextDouble();

                coordinates.setX(x);
                coordinates.setY(y);
                validInput = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Invalid formats. x is integer less than 630 and y is double");
                scanner.nextLine();
            }
        }

        return coordinates;
    }

    private double addHeight(Scanner scanner){
        double height = 0.0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter person height: ");
                height = scanner.nextDouble();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid formats. height is double");
                scanner.nextLine();
            }
        }
        return height;
    }

    private EyeColor addEyeColor(Scanner scanner) {
        EyeColor eyeColor = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter person eye color: ");
                String input = scanner.next().toUpperCase();
                eyeColor = EyeColor.valueOf(input);
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Valid eye colors are RED, BLACK and ORANGE");
                scanner.nextLine();
            }
        }
        return eyeColor;
    }

    private HairColor addHairColor(Scanner scanner) {
        HairColor hairColor = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter person hair color: ");
                String input = scanner.next().toUpperCase();
                hairColor = HairColor.valueOf(input);
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Valid hair colors are GREEN, BLUE, YELLOW and BROWN");
                scanner.nextLine();
            }
        }
        return hairColor;
    }

    private Country addCountry(Scanner scanner) {
        Country country = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter nationality: ");
                String input = scanner.next().toUpperCase();
                country = Country.valueOf(input);
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. valid colors are RUSSIA, GERMANY, ITALY, THAILAND and JAPAN");
                scanner.nextLine();
            }
        }
        return country;
    }

    private Location addLocation(Scanner scanner) {
        Location location = new Location();
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter person location");
                System.out.print("x: ");
                float x = scanner.nextFloat();
                System.out.print("y: ");
                Float y = scanner.nextFloat();
                System.out.print("name: ");
                String name = scanner.next();
                if(name.length() > 530) {
                    throw new Exception("length of name too long");
                }

                location.setX(x);
                location.setY(y);
                location.setName(name);
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid formats. x and y are float and name is string");
                scanner.nextLine();
            }
        }

        return location;
    }


    //Helper method for adding person to collection ( For commands 'add' and 'add_if_max' )
    public void add(Person person) {
        personTreeSet.add(person);
        System.out.println(person.getName() + " has been added to the collection");
    }

    //Helper method for 'ADD' command
    public void addPerson() {
        Scanner scanner = new Scanner(System.in);
        Person newPerson = readPersonFromScanner(scanner);
        add(newPerson);
    }

    //Helper method for 'UPDATE' command
    public void updatePerson(Person person){
        Scanner scanner = new Scanner(System.in);

        //collect name
        System.out.print("Enter name: ");
        String name = scanner.next();

        //collect coordinates
        Coordinates coordinates = addCoordinate(scanner);
        

        System.out.println("Creation date cannot be changed");

        //collect height
        double height = addHeight(scanner);

        //collect Eye color, hair color and country
        EyeColor eyeColor = addEyeColor(scanner);
        HairColor hairColor = addHairColor(scanner);
        Country nationality = addCountry(scanner);

        //collect location
        Location location = addLocation(scanner);

        person.setName(name);
        person.setCoordinates(coordinates);
        person.setHeight(height);
        person.setEyeColor(eyeColor);
        person.setHairColor(hairColor);
        person.setNationality(nationality);
        person.setLocation(location);

        System.out.println("Person with id " + person.getId() + " has been updated");
    }

    //Helper method for 'REMOVE_BY_ID' command
    public void removePerson(Person person){
        personTreeSet.remove(person);
    }

    //Helper method for 'CLEAR COMMAND'
    public void clear(){
        ArrayList<Person> personList = new ArrayList<>(personTreeSet);
        personList.forEach(personTreeSet::remove);
        System.out.println("Collection have been cleared");
    }

    //Helper method for 'SHOW' command
    public void show(){
        if(personTreeSet.isEmpty()){
            System.out.println("THE COLLECTION IS EMPTY");
        } else {
            System.out.println("DISPLAYING THE COLLECTION DATA");
            for (Person person : personTreeSet) {
                String eachPersonString = person.toString();
                System.out.println(eachPersonString);
                System.out.println();
            }
        }

    }

    //Helper method for 'INFO' command
    public void info() {
        System.out.println("Collection type: " + personTreeSet.getClass());
        String initializationDate = getInitializationDate();
        System.out.println("Initialization Date: " + initializationDate);
        int elementsCount = personTreeSet.size();
        System.out.println("Number of persons: " + elementsCount);
    }
    //Helper method for the 'info' method
    private String getInitializationDate(){
        String date = "";

        try{
            Path path = Paths.get("storage.xml");
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime fileTime = attributes.creationTime();
            Instant instant = fileTime.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            date = localDateTime.format(formatter);
        } catch (IOException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        return date;
    }

    //Helper method for 'MAX_BY_ID' command
    public Person getMaxById() {
        return personTreeSet.isEmpty() ? null : personTreeSet.last();
    }

    //Helper method for 'AVERAGE_OF_HEIGHT' command
    public void printAverageHeight(){
        if (personTreeSet.isEmpty()) {
            System.out.println("Collection is empty");
            return;
        }

        double averageHeight = personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .average()
                .orElse(0.0);

        System.out.printf("Average height: %.2f%n", averageHeight);
    }

    //Helper method for 'COUNT_BY_LOCATION' command
    public int countByLocation(String location){
        int count = 0;
        for(Person person : personTreeSet){
            if(person.getLocation().getName().equalsIgnoreCase(location)) {
                count++;
            }
        }

        return count;
    }

    //Helper method for 'REMOVE_LOWER' command
    public void removeLower(String idString){
        try{
            int inputtedId = Integer.parseInt(idString);
            ArrayList<Person> toRemove = new ArrayList<>();
            Person personWithId = new Person();
            int position = 0;

            for (Person person : personTreeSet) {
                position += 1;
                if(person.getId() == inputtedId) {
                    personWithId = person;
                    break;
                }
            }
            System.out.println(position);
            if(personWithId.getId() == null) {
                System.out.println("No person in collection exists with ID " + inputtedId);
                return;
            }

            for (Person person : personTreeSet) {
                if(personWithId.getId() > person.getId()) {
                    toRemove.add(person);
                }
            }

            if(toRemove.isEmpty()){
                System.out.println("Person with id " + inputtedId + " is the lowest");
            } else{
                toRemove.forEach(personTreeSet::remove);
                System.out.println("Removed all elements lower");
            }


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("ID must be an integer");
        }
    }

    //Get max height from collection to compare with new person and trigger 'add if max' command
    public double getMaxHeight() {
        return personTreeSet.stream()
                .mapToDouble(Person::getHeight)
                .max()
                .orElse(0.0);
    }


}
