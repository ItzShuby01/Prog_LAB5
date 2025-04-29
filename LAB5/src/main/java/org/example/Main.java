package org.example;

import org.example.utils.*;

//UPLOAD TO HELIOS : LAB5 % scp -P 2222 target/java5-1.0-SNAPSHOT.jar s463221@se.ifmo.ru:~

public class Main {
//scp -P 2222 <путь до jar-архива> <твой логин>@se.ifmo.ru:~
  public static void main(String[] args) {
    try (ConsoleIOService ioService = new ConsoleIOService()) {
      String fileName = System.getenv("COLLECTION_FILE_PATH");

      CollectionManager collectionManager = new CollectionManager(fileName);
      FileManager fileManager = new FileManager(collectionManager);
      PersonIOService personIOService = new PersonIOService(ioService, collectionManager);

      // setting environmental variables.
      // if you want to set environmental variables in your IDE, then set it up

                  if (fileName == null) {
                      System.out.println("Environment variable COLLECTION_FILE_PATH is not set or is empty.");
                      return;
                  }

                  // 1. for environmental variable
                  fileManager.loadCollectionFromXml(fileName);

      // 2. If you don’t want to use environmental variables in your IDE , uncomment the line below,
      //  and then comment/remove lines 16 to 24
      //fileManager.loadCollectionFromXml("storage.xml");

      // 3. Uncomment the line below if you want to pass the name of the file directly in the CLI(
      // terminal)
      // fileManager.loadCollectionFromXml(args[0]);
      // Type this on terminal if you want to use the above command (java -jar
      // java5-1.0-SNAPSHOT.jar ../storage.xml)

      CommandManager commandManager =
          new CommandManager(collectionManager, personIOService, ioService);
      ConsoleManager consoleManager = new ConsoleManager(commandManager);

      // System.out.println("JAXB Available: " +
      // javax.xml.bind.JAXBContext.class.getProtectionDomain().getCodeSource().getLocation());

      consoleManager.interactiveMode();

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
