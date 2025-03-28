package org.example.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.example.collection.Person;
import org.example.collection.PersonList;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FileManager {
    private final CollectionManager collectionManager;

    public FileManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ArrayList<String> readScript(String filePath) {
        ArrayList<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().replace("\r", "");
                if (!line.isEmpty()) {
                    commands.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading script: " + e.getMessage());
        }
        return commands;
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
        }
    }

    //Helper for 'SAVE'
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
        }
    }
}

