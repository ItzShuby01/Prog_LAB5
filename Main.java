package org.example;

import org.example.utils.*;


public class Main {


    public static void main(String[] args) {
        try{
            CollectionManager collectionManager = new CollectionManager();
            FileManager fileManager = new FileManager(collectionManager);

            //setting environmental variables.
            //if you want to set environmental variables in your IDE, then set it up 
            String fileName = System.getenv("COLLECTION_FILE_PATH");

            if (fileName == null || fileName.isEmpty()) {
                System.out.println("Environment variable COLLECTION_FILE_PATH is not set or is empty.");
                return;
            }

            // 1. for environmental variable
            fileManager.loadCollectionFromXml(fileName);

             //2. If you don’t want to use environmenatl variables in your IDE , uncomment the line below,  and then comment/remove lines 16 to 24
//            fileManager.loadCollectionFromXml("storage.xml");

            //3. Uncomment the line below if you want to pass the name of the file directly in the CLI( terminal)
           //fileManager.loadCollectionFromXml(args[0]);
                //Type this on terminal if you want to use the above command (java -jar java5-1.0-SNAPSHOT.jar ../storage.xml)

            CommandManager commandManager = new CommandManager(collectionManager);
            ConsoleManager consoleManager = new ConsoleManager(commandManager);

            consoleManager.interactiveMode();

        } catch (Exception e) {
             System.err.println("Error: " + e.getMessage());
        }
    }
}
