package org.example.utils;

import org.example.commands.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class CommandManager {
    private final CollectionManager collectionManager;
    private final LinkedList<String> commandHistory = new LinkedList<>();

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void executeCommand(String command){
        String[] commandArray = command.split(" ");
        String c = commandArray[0];
        String arg = "";

        if(commandArray.length == 2){
            arg = commandArray[1];
        }

        switch (c) {
            case "help":
                new Help().execute();
                addToHistory(c);
                break;
            case "exit":
                new Exit().execute();
                addToHistory(c);
                break;
            case "info":
                new Info(collectionManager).execute();
                addToHistory(c);
                break;
            case "show":
                new Show(collectionManager).execute();
                addToHistory(c);
                break;
            case "save":
                new Save(collectionManager).execute();
                addToHistory(c);
                break;
            case "add":
                new Add(collectionManager).execute();
                addToHistory(c);
                break;
            case "clear":
                new Clear(collectionManager).execute();
                addToHistory(c);
                break;
            case "history":
                new History(commandHistory).execute();
                addToHistory(c);
                break;
            case "max_by_id":
                new MaxById(collectionManager).execute();
                addToHistory(c);
                break;
            case "average_of_height":
                new AverageOfHeight(collectionManager).execute();
                addToHistory(c);
                break;
            case "add_if_max":
                new AddIfMax(collectionManager).execute();
                addToHistory(c);
                break;

            //COMMANDS WITH ARGUMENTS
            case "update":
                new Update(collectionManager).execute(arg);
                addToHistory(c);
                break;
            case "remove_by_id":
                new RemoveById(collectionManager).execute(arg);
                addToHistory(c);
                break;
            case "count_by_location":
                new CountByLocation(collectionManager).execute(arg);
                addToHistory(c);
                break;
            case "remove_lower":
                new RemoveLower(collectionManager).execute(arg);
                addToHistory(c);
                break;
            case "execute_script":
                readCommandsFromScript(arg);
                addToHistory(c);
                break;
            default:
                System.out.println("Command not found. Enter 'help' to see the manual");
        }


        System.out.println("////////////////////////////////////////////////////////////");
        System.out.println("\n");
    }

    private void addToHistory(String commandName) {
        // Keep only the last 7 commands.
        if (commandHistory.size() == 7) {
            commandHistory.removeFirst();
        }
        commandHistory.add(commandName);
    }

    //method for 'EXECUTE_SCRIPT' command
    private void readCommandsFromScript(String filePath){
        FileManager fileManager = new FileManager(collectionManager);
        ArrayList<String> commands = fileManager.loadCommandsFromScript(filePath);

        for (String command : commands) {
            System.out.println("Executing command: " +command);
            executeCommand(command);
        }
    }
}
