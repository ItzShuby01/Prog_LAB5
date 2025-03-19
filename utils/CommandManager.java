package org.example.utils;

import org.example.commands.*;

import java.util.*;
import java.util.function.Consumer;

//This class Manages commands, their execution, and command history.
public class CommandManager {
    private final CollectionManager collectionManager;
    private final ConsoleManager consoleManager;
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private final HashMap<String, Consumer<String>> commandMap = new HashMap<>();
    private final Set<String> argumentRequiredCommands = new HashSet<>();
    private final HashMap<String, String> commandDescriptionMap = new HashMap<>();

    //Constructor for CommandManager  with reference to  CollectionManager.
    public CommandManager(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
        initializeCommands();
    }


    private void initializeCommands() {
       //Argument validation - make sure commands that require arguments are passed arguments
        argumentRequiredCommands.add("update");
        argumentRequiredCommands.add("remove_by_id");
        argumentRequiredCommands.add("count_by_location");
        argumentRequiredCommands.add("remove_lower");
        argumentRequiredCommands.add("execute_script");

        //Initializes all available commands and stores them in a Command map.
        commandMap.put("help", arg -> new Help(this).execute());
        commandDescriptionMap.put("help", "help: display help on available commands");

        commandMap.put("exit", arg -> new Exit(consoleManager).execute());
        commandDescriptionMap.put("exit", "exit: exit the program (without saving to file)");

        commandMap.put("info", arg -> new Info(collectionManager).execute());
        commandDescriptionMap.put("info", "info: print collection information (type, initialization date, number of elements, etc.) to standard output");

        commandMap.put("show", arg -> new Show(collectionManager).execute());
        commandDescriptionMap.put("show", "show: print all elements of the collection to standard output");

        commandMap.put("save", arg -> new Save(collectionManager).execute());
        commandDescriptionMap.put("save", "save: save collection to file");

        commandMap.put("add", arg -> new Add(collectionManager).execute());
        commandDescriptionMap.put("add", "add {element}: add a new item to the collection");

        commandMap.put("clear", arg -> new Clear(collectionManager).execute());
        commandDescriptionMap.put("clear", "clear: clear collection");

        commandMap.put("history", arg -> new History(commandHistory).execute());
        commandDescriptionMap.put("history", "history: print the last 7 commands (without their arguments)");


        commandMap.put("max_by_id", arg -> new MaxById(collectionManager).execute());
        commandDescriptionMap.put("max_by_id", "max_by_id: output any object from the collection whose id field value is maximum");

        commandMap.put("average_of_height", arg -> new AverageOfHeight(collectionManager).execute());
        commandDescriptionMap.put("average_of_height","average_of_height: output the average height field value for all elements in a collection");

        commandMap.put("add_if_max", arg -> new AddIfMax(collectionManager).execute());
        commandDescriptionMap.put("add_if_max", "add_if_max {element}: add a new element to a collection if its value is greater than the value of the largest element in that collection");




        // Adding commands that require arguments
        commandMap.put("update", arg -> new Update(collectionManager).execute(arg));
        commandDescriptionMap.put("update", "update id {element}: update the value of the collection element whose id is equal to the given one");

        commandMap.put("remove_by_id", arg -> new RemoveById(collectionManager).execute(arg));
        commandDescriptionMap.put("remove_by_id", "remove_by_id id: remove an element from a collection by its id");

        commandMap.put("count_by_location", arg -> new CountByLocation(collectionManager).execute(arg));
        commandDescriptionMap.put("count_by_location", "count_by_location location: output the number of elements whose location field value is equal to the specified one");

        commandMap.put("remove_lower", arg -> new RemoveLower(collectionManager).execute(arg));
        commandDescriptionMap.put("remove_lower", "remove_lower {element}: remove all elements from the collection that are less than the specified number");

        commandMap.put("execute_script", this::readCommandsFromScript);
        commandDescriptionMap.put("execute_script", "execute_script file_name: read and execute the script from the specified file. The script contains commands in the same form in which the user enters them in interactive mode");

    }

    // Getter for commands descriptions map
    public Map<String, String> getCommandDescriptionMap() {
        return new HashMap<>(commandDescriptionMap);
    }


      //Executes the given command by retrieving it from the command map.
      //If the command is not found, it prints an error message.
      public void executeCommand(String command) {
          String[] commandArray = command.split(" ", 2);
          String commandName = commandArray[0];
          String arg = (commandArray.length == 2) ? commandArray[1] : "";

          if (commandMap.containsKey(commandName)) {
              // Check argument requirements
              if (argumentRequiredCommands.contains(commandName)) {
                  if (arg.isEmpty()) {
                      System.out.println("Error: Command '" + commandName + "' requires an argument.");
                      return;
                  }
              } else {
                  if (!arg.isEmpty()) {
                      System.out.println("Error: Command '" + commandName + "' does not take an argument.");
                      return;
                  }
              }

              // Execute command if valid
              commandMap.get(commandName).accept(arg);
              addToHistory(commandName);
          } else {
              System.out.println("Command not found. Enter 'help' to see the manual");
          }

          System.out.println("////////////////////////////////////////////////////////////\n");
      }
    //Adds the commands (without arguments) to the command history.
    private void addToHistory(String commandName) {
        if (commandHistory.size() == 7) {
            commandHistory.removeFirst();
        }
        commandHistory.add(commandName);
    }

    //Reads and executes commands from a script file.
    private void readCommandsFromScript(String filePath) {
        FileManager fileManager = new FileManager(collectionManager);
        for (String command : fileManager.loadCommandsFromScript(filePath)) {
            System.out.println("Executing command: " + command);
            executeCommand(command);
        }
    }

}
