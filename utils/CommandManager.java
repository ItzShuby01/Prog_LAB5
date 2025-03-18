package org.example.utils;

import org.example.commands.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Consumer;

//This class Manages commands, their execution, and command history.
public class CommandManager {
    private final CollectionManager collectionManager;
    private final ConsoleManager consoleManager;
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private final HashMap<String, Consumer<String>> commandMap = new HashMap<>();

    //Constructor for CommandManager  with reference to  CollectionManager.
    public CommandManager(CollectionManager collectionManager, ConsoleManager consoleManager) {
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
        initializeCommands();
    }

    //Initializes all available commands and stores them in a Command map.
    private void initializeCommands() {
        commandMap.put("help", arg -> new Help().execute());
        commandMap.put("exit", arg -> new Exit(consoleManager).execute());
        commandMap.put("info", arg -> new Info(collectionManager).execute());
        commandMap.put("show", arg -> new Show(collectionManager).execute());
        commandMap.put("save", arg -> new Save(collectionManager).execute());
        commandMap.put("add", arg -> new Add(collectionManager).execute());
        commandMap.put("clear", arg -> new Clear(collectionManager).execute());
        commandMap.put("history", arg -> new History(commandHistory).execute());
        commandMap.put("max_by_id", arg -> new MaxById(collectionManager).execute());
        commandMap.put("average_of_height", arg -> new AverageOfHeight(collectionManager).execute());
        commandMap.put("add_if_max", arg -> new AddIfMax(collectionManager).execute());

        // Adding commands that require arguments
        commandMap.put("update", arg -> new Update(collectionManager).execute(arg));
        commandMap.put("remove_by_id", arg -> new RemoveById(collectionManager).execute(arg));
        commandMap.put("count_by_location", arg -> new CountByLocation(collectionManager).execute(arg));
        commandMap.put("remove_lower", arg -> new RemoveLower(collectionManager).execute(arg));
        commandMap.put("execute_script", this::readCommandsFromScript);
    }

      //Executes the given command by retrieving it from the command map.
      //If the command is not found, it prints an error message.
    public void executeCommand(String command) {
        String[] commandArray = command.split(" ", 2);
        String commandName = commandArray[0];
        String arg = (commandArray.length == 2) ? commandArray[1] : "";

        if (commandMap.containsKey(commandName)) {
            commandMap.get(commandName).accept(arg);
            addToHistory(commandName);
        } else {
            System.out.println("Command not found. Enter 'help' to see the manual");
        }

        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n");
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
