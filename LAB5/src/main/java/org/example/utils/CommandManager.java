package org.example.utils;

import java.util.*;
import java.util.function.Consumer;
import org.example.commands.*;

public class CommandManager {
  private final CollectionManager collectionManager;
  private final PersonIOService personIOService;
  private final IOService ioService;
  private final LinkedList<String> commandHistory = new LinkedList<>();
  public final HashMap<String, Consumer<String>> commandMap = new HashMap<>();
  private final Set<String> argumentRequiredCommands = new HashSet<>();
  private final HashMap<String, Command> commandInstances = new HashMap<>();
  public final Map<String, ScriptCommand> scriptCommandMap = new HashMap<>();

  private boolean exitRequested = false;

  public CommandManager(
      CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
    this.collectionManager = collectionManager;
    this.personIOService = personIOService;
    this.ioService = ioService;
    initializeCommands();
  }

  //  Register Commands
  private void initializeCommands() {
    argumentRequiredCommands.add("update");
    argumentRequiredCommands.add("remove_by_id");
    argumentRequiredCommands.add("count_by_location");
    argumentRequiredCommands.add("execute_script");

    Add addCmd = new Add(collectionManager, personIOService, ioService);
    AddIfMax addIfMaxCmd = new AddIfMax(collectionManager, personIOService, ioService);
    Update updateCmd = new Update(collectionManager, ioService, personIOService);

    scriptCommandMap.put("add", addCmd);
    scriptCommandMap.put("add_if_max", addIfMaxCmd);
    scriptCommandMap.put("update", updateCmd);

    // Register commands with their instances
    registerCommand("help", new Help(this, ioService));
    registerCommand("exit", new Exit(this, ioService));
    registerCommand("info", new Info(collectionManager, ioService));
    registerCommand("show", new Show(collectionManager, ioService));
    registerCommand("save", new Save(collectionManager));
    registerCommand("add", new Add(collectionManager, personIOService, ioService));
    registerCommand("clear", new Clear(collectionManager, ioService));
    registerCommand("history", new History(commandHistory, ioService));
    registerCommand("max_by_id", new MaxById(collectionManager, ioService));
    registerCommand("average_of_height", new AverageOfHeight(collectionManager, ioService));
    registerCommand("add_if_max", new AddIfMax(collectionManager, personIOService, ioService));
    registerCommand("update", new Update(collectionManager, ioService, personIOService));
    registerCommand("remove_by_id", new RemoveById(collectionManager, ioService));
    registerCommand("count_by_location", new CountByLocation(collectionManager, ioService));
    registerCommand("remove_lower", new RemoveLower(collectionManager, ioService, personIOService));
    registerCommand(
        "execute_script", new ExecuteScript(this, ioService, new FileManager(collectionManager)));
  }

  private void registerCommand(String name, Command command) {
    if (argumentRequiredCommands.contains(name)) {
      commandMap.put(name, arg -> command.execute(arg));
    } else {
      commandMap.put(name, arg -> command.execute());
    }
    commandInstances.put(name, command);
  }

  public void executeCommand(String input) {
    String[] parts = input.split(" ", 2);
    String cmdName = parts[0];
    String arg = parts.length > 1 ? parts[1] : "";

    if (!commandMap.containsKey(cmdName)) {
      ioService.print("Command not found. Enter 'help' for assistance.");
      return;
    }

    if (argumentRequiredCommands.contains(cmdName)) {
      if (arg.isEmpty()) {
        ioService.print("Error: Command requires an argument.");
        return;
      }
    } else if (!arg.isEmpty()) {
      ioService.print("Error: Command does not take an argument.");
      return;
    }
    try {
      commandMap.get(cmdName).accept(arg);
      addToHistory(cmdName);
    } catch (Exception e) {
      ioService.print("Error: " + e.getMessage());
    }
  }

  // Commands Descriptions GETTER
  public Map<String, String> getCommandDescriptions() {
    Map<String, String> descriptions = new HashMap<>();
    commandInstances.forEach((name, cmd) -> descriptions.put(name, cmd.getDescription()));
    return descriptions;
  }

  public boolean isExitRequested() {
    return exitRequested;
  }

  public void requestExit() {
    exitRequested = true;
  }

  public void addToHistory(String cmdName) {
    if (commandHistory.size() == 7) commandHistory.removeFirst();
    commandHistory.add(cmdName);
  }
}
