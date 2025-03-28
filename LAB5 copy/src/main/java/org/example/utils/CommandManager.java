package org.example.utils;

import org.example.commands.*;

import java.util.*;
import java.util.function.Consumer;

//This class Manages commands, their execution, and command history.
public class CommandManager {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private final HashMap<String, Consumer<String>> commandMap = new HashMap<>();
    private final Set<String> argumentRequiredCommands = new HashSet<>();
    private final HashMap<String, String> commandDescriptionMap = new HashMap<>();
    private boolean exitRequested = false;


    public CommandManager(CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
        this.ioService = ioService;
        initializeCommands();
    }

    public boolean isExitRequested() {
        return exitRequested;
    }

    public void requestExit() {
        exitRequested = true;
    }


    private void initializeCommands() {
        //Argument validation - to make sure commands that require arguments are passed arguments
        argumentRequiredCommands.add("update");
        argumentRequiredCommands.add("remove_by_id");
        argumentRequiredCommands.add("count_by_location");
        argumentRequiredCommands.add("remove_lower");
        argumentRequiredCommands.add("execute_script");

        //Initializes all available commands and stores them in a Command map.
        commandMap.put("help", arg -> new Help(this).execute());
        commandDescriptionMap.put("help", "help: display help on available commands");

        commandMap.put("exit", arg -> new Exit(this, ioService).execute());
        commandDescriptionMap.put("exit", "exit: exit the program (without saving to file)");

        commandMap.put("info", arg -> new Info(collectionManager, ioService).execute());
        commandDescriptionMap.put("info", "info: print collection information (type, initialization date, number of elements, etc.) to standard output");

        commandMap.put("show", arg -> new Show(collectionManager, ioService).execute());
        commandDescriptionMap.put("show", "show: print all elements of the collection to standard output");

        commandMap.put("save", arg -> new Save(collectionManager).execute());
        commandDescriptionMap.put("save", "save: save collection to file");

        commandMap.put("add", arg -> new Add(collectionManager, personIOService, ioService).execute(arg));
        commandDescriptionMap.put("add", "add {element}: add a new item to the collection");

        commandMap.put("clear", arg -> new Clear(collectionManager, ioService).execute());
        commandDescriptionMap.put("clear", "clear: clear collection");

        commandMap.put("history", arg -> new History(commandHistory, ioService).execute());
        commandDescriptionMap.put("history", "history: print the last 7 commands (without their arguments)");


        commandMap.put("max_by_id", arg -> new MaxById(collectionManager, ioService).execute());
        commandDescriptionMap.put("max_by_id", "max_by_id: output any object from the collection whose id field value is maximum");

        commandMap.put("average_of_height", arg -> new AverageOfHeight(collectionManager, ioService).execute());
        commandDescriptionMap.put("average_of_height", "average_of_height: output the average height field value for all elements in a collection");

        commandMap.put("add_if_max", arg -> new AddIfMax(collectionManager, personIOService, ioService).execute());
        commandDescriptionMap.put("add_if_max", "add_if_max {element}: add a new element to a collection if its value is greater than the value of the largest element in that collection");


        // Adding commands that require arguments
        commandMap.put("update", arg -> new Update(collectionManager, personIOService, ioService).execute(arg));
        commandDescriptionMap.put("update", "update id {element}: update the value of the collection element whose id is equal to the given one");

        commandMap.put("remove_by_id", arg -> new RemoveById(collectionManager, ioService).execute(arg));
        commandDescriptionMap.put("remove_by_id", "remove_by_id id: remove an element from a collection by its id");

        commandMap.put("count_by_location", arg -> new CountByLocation(collectionManager, ioService).execute(arg));
        commandDescriptionMap.put("count_by_location", "count_by_location location: output the number of elements whose location field value is equal to the specified one");

        commandMap.put("remove_lower", arg -> new RemoveLower(collectionManager, ioService).execute(arg));
        commandDescriptionMap.put("remove_lower", "remove_lower {element}: remove all elements from the collection that are less than the specified number");

        commandMap.put("execute_script", this::executeScript);
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
            if (argumentRequiredCommands.contains(commandName)) {
                if (arg.isEmpty()) {
                    ioService.print("Error: Command '" + commandName + "' requires an argument.");
                    return;
                }
            } else {
                if (!arg.isEmpty()) {
                    ioService.print("Error: Command '" + commandName + "' does not take an argument.");
                    return;
                }
            }

            commandMap.get(commandName).accept(arg);
            addToHistory(commandName);
        } else {
            ioService.print("Command not found. Enter 'help' to see the manual");
        }
    }

    private void addToHistory(String commandName) {
        if (commandHistory.size() == 7) {
            commandHistory.removeFirst();
        }
        commandHistory.add(commandName);
    }

    //Reads and executes commands from a script file.
    private void executeScript(String filePath) {
        FileManager fileManager = new FileManager(collectionManager);
        List<String> lines = fileManager.readScript(filePath);
        int index = 0;

        while (index < lines.size()) {
            String line = lines.get(index).trim();
            if (line.isEmpty()) {
                index++;
                continue;
            }

            if (line.equals("add")) {
                // case 1: Validate there are enough lines for the 'add' command
                if (index + 10 >= lines.size()) {
                    ioService.print("Error: Incomplete 'add' command at line " + (index + 1));
                    return;
                }
                // Collect the next 10 lines as arguments for 'add'
                StringBuilder argBuilder = new StringBuilder();
                for (int i = 1; i <= 10; i++) {
                    argBuilder.append(lines.get(index + i).trim()).append("\n");
                }
                String argString = argBuilder.toString().trim();

                // Execute the 'add' command
                commandMap.get("add").accept(argString);
                index += 11;


            } else {
                // case 2: Handle normal commands (split them  into 'command' + 'args')
                String[] parts = line.split(" ", 2);
                String commandName = parts[0];
                String arg = parts.length > 1 ? parts[1] : "";

                if (commandMap.containsKey(commandName)) {
                    commandMap.get(commandName).accept(arg);
                    addToHistory(commandName);
                } else {
                    ioService.print("Command not found: " + commandName);
                }
                index++;
            }
        }
    }
}