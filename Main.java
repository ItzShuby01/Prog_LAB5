package org.example;

import org.example.utils.*;


public class Main {


    public static void main(String[] args) {
        try{
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(collectionManager);

            //setting environmental variables.
            //if you want to set environmental variables in your IDE, then set it up
            //else remove lines 16 to 27
            String fileName = System.getenv("COLLECTION_FILE_PATH");

            if (fileName == null || fileName.isEmpty()) {
                System.out.println("Environment variable COLLECTION_FILE_PATH is not set or is empty.");
                return;
            }

            //for environmental variable
            fileManager.loadCollectionFromXml(fileName);

//            fileManager.loadCollectionFromXml("storage.xml");

               //Pass the name of the file directly in the CLI( terminal)
           //fileManager.loadCollectionFromXml(args[0]);
                //Type this on terminal if you want to use the above command (java -jar java5-1.0-SNAPSHOT.jar ../storage.xml)

            CommandManager commandManager = new CommandManager(collectionManager);
            ConsoleManager consoleManager = new ConsoleManager(commandManager);

            consoleManager.interactiveMode();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}