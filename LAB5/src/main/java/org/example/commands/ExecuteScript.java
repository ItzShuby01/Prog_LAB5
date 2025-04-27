package org.example.commands;

import org.example.utils.CommandManager;
import org.example.utils.IOService;
import org.example.utils.FileManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ExecuteScript implements Command {
    public static final String DESCRIPTION = "execute_script file_name: read and execute the script from the specified file. The script contains commands in the same form in which the user enters them in interactive mode";
    private final CommandManager commandManager;
    private final IOService ioService;
    private final FileManager fileManager;
    private final Set<String> executingScripts = new HashSet<>();

    public ExecuteScript(CommandManager commandManager, IOService ioService, FileManager fileManager) {
        this.commandManager = commandManager;
        this.ioService = ioService;
        this.fileManager = fileManager;
    }

    @Override
    public void execute(String filePath) {
        if (executingScripts.contains(filePath)) {
            ioService.print("Recursion detected: " + filePath);
            return;
        }

        List<String> scriptLines = fileManager.readScript(filePath);
        if (scriptLines.isEmpty()) return;

        executingScripts.add(filePath);
        try {
            processScript(scriptLines);
        } finally {
            executingScripts.remove(filePath);
        }
    }

    private void processScript(List<String> scriptLines) {
        int index = 0;
        while (index < scriptLines.size()) {
            String line = scriptLines.get(index).trim();
            if (line.isEmpty() || line.startsWith("#")) { // Skip comments
                index++;
                continue;
            }

            String[] parts = line.split(" ", 2);
            String commandName = parts[0];
            String arg = parts.length > 1 ? parts[1] : "";

            // Handle script commands (multi-line)
            ScriptCommand scriptCmd = commandManager.scriptCommandMap.get(commandName);
            if (scriptCmd != null) {
                index = scriptCmd.scriptExecution(scriptLines, index);
                commandManager.addToHistory(commandName);
                continue;
            }

            // Handle no-argument commands
            Consumer<String> cmd = commandManager.commandMap.get(commandName);
            if (cmd != null) {
                try {
                    cmd.accept(arg);
                    commandManager.addToHistory(commandName);
                } catch (Exception e) {
                    ioService.print("Error in command '" + commandName + "': " + e.getMessage());
                }
            } else {
                ioService.print("Unknown command: " + commandName);
            }
            index++;
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}