package org.example.utils;

import java.util.Scanner;

public class ConsoleManager {
    private CommandManager commandManager;
    private Scanner scanner;
    private boolean running = true;

    public ConsoleManager() {}

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.scanner = new Scanner(System.in);
    }

    public void stop() {
        running = false;
    }

    public void interactiveMode() {
        System.out.println("WELCOME TO THE PERSON COLLECTION APP");
        System.out.println("Enter 'help' for manual, 'exit' to stop the program");

        while (running) {
            System.out.print("Enter command> ");
            String command = scanner.nextLine().trim();
            if (!command.isEmpty()) {
                commandManager.executeCommand(command);
            }
        }
    }
}
