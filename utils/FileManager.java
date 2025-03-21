package org.example.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.example.collection.Person;
import org.example.collection.PersonList;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileManager {
    private final CollectionManager collectionManager;

    public FileManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ArrayList<String> loadCommandsFromScript(String filePath) {
        ArrayList<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder commandBlock = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Skip empty lines

                // Check if line starts a new command (e.g., "add", "save")
                if (isCommand(line)) {
                    if (commandBlock.length() > 0) {
                        commands.add(commandBlock.toString().trim());
                        commandBlock.setLength(0); // Reset
                    }
                    commandBlock.append(line);
                } else {
                    // Append subsequent lines as arguments (for multi-line commands like 'add')
                    commandBlock.append("\n").append(line);
                }
            }
            // Add the last command block
            if (commandBlock.length() > 0) {
                commands.add(commandBlock.toString().trim());
            }
        } catch (IOException e) {
            System.out.println("Error loading script: " + e.getMessage());
        }
        return commands;
    }

    private boolean isCommand(String line) {
        // Add all commands that require arguments (like 'add')
        return line.matches("^(add|update|remove_by_id|count_by_location|remove_lower|execute_script)$");
    }
    public void loadCollectionFromXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PersonList personList = (PersonList) unmarshaller.unmarshal(new File(filePath));
            List<Person> persons = personList.getPersons();
            if (persons == null) {
                persons = new ArrayList<>();
            }
            TreeSet<Person> loadedCollection = new TreeSet<>(persons);
            collectionManager.setPersonTreeSet(loadedCollection);
            System.out.println("Collection loaded successfully!");
        } catch (JAXBException e) {
            System.out.println("Error loading collection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCollectionToXml(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Save file path cannot be null or empty.");
        }
        try {
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonList personList = new PersonList(collectionManager.getAllPersons());
            marshaller.marshal(personList, new File(filePath));
            System.out.println("Collection saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving collection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

