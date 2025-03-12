package org.example.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.collection.Person;


import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class FileManager {
    private final CollectionManager collectionManager;

    public FileManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public ArrayList<String> loadCommandsFromScript(String filePath){
        ArrayList<String> commands = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String command;
            while ((command = bufferedReader.readLine()) != null) {
                commands.add(command);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return commands;
    }

    public void loadCollectionFromXml(String filePath) {
        try {
            // Create a JAXB context for PersonCollection class
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Deserialize from XML file
            CollectionManager collection = (CollectionManager) unmarshaller.unmarshal(new File(filePath));

            // Get the TreeSet of Persons
            TreeSet<Person> loadedCollection = collection.getPersonTreeSet();
            collectionManager.setPersonTreeSet(loadedCollection);
            System.out.println("Collection loaded successfully!");
        } catch (JAXBException e) {
            System.out.println("Error loading collection: " + e.getMessage());
        }
    }

    public void saveCollectionToXml(String filePath) {
        try {
            // Create JAXB context for the CollectionManager class
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();

            // Format the output for better readability
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal the CollectionManager object to XML and save it to the file
            marshaller.marshal(collectionManager, new File(filePath));

            System.out.println("Collection saved to " + filePath);
        } catch (JAXBException e) {
            System.out.println("Error saving collection: " + e.getMessage());
        }
    }
}
