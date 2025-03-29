package org.example;

import org.example.utils.*;

public class Main {


    public static void main(String[] args) {
        try ( ConsoleIOService ioService = new ConsoleIOService()){
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(collectionManager);
            PersonIOService personIOService= new PersonIOService(ioService, collectionManager);

            //setting environmental variables.
            //if you want to set environmental variables in your IDE, then set it up
            String fileName = System.getenv("COLLECTION_FILE_PATH");

            if (fileName == null) {
                System.out.println("Environment variable COLLECTION_FILE_PATH is not set or is empty.");
                return;
            }

            // 1. for environmental variable
            fileManager.loadCollectionFromXml(fileName);

            //for running in IDE ( without env var )
            //fileManager.loadCollectionFromXml("storage.xml");

            //3. for running in the CLI( terminal) by passing the name of the file as argument
            //fileManager.loadCollectionFromXml(args[0]);
            //Type this on terminal if you want to use the above command (java -jar java5-1.0-SNAPSHOT.jar ../storage.xml)

            CommandManager commandManager = new CommandManager(collectionManager,personIOService, ioService)
            ConsoleManager consoleManager = new ConsoleManager(commandManager);

            consoleManager.interactiveMode();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
