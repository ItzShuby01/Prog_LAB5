package org.example.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.example.collection.Person;
import org.example.collection.PersonList;


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
            JAXBContext context = JAXBContext.newInstance(PersonList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            PersonList personList = (PersonList) unmarshaller.unmarshal(new File(filePath));

            // Convert the list to a TreeSet (if needed) and update the collection
            TreeSet<Person> loadedCollection = new TreeSet<>(personList.getPersons());
            collectionManager.setPersonTreeSet(loadedCollection);
            System.out.println("Collection loaded successfully!");
        } catch (JAXBException e) {
            System.out.println("Error loading collection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCollectionToXml(String filePath) {
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

