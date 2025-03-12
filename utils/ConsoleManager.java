package org.example.utils;

import java.util.Scanner;

public class ConsoleManager {
    private final CommandManager commandManager;
    private final Scanner scanner;


    public ConsoleManager(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.scanner = new Scanner(System.in);
    }

    public void interactiveMode() {
        System.out.println("WELCOME TO THE PERSON COLLECTION APP \n");
        System.out.println("Enter 'help' for manual, 'exit' to stop the program");

        while (true) {
            System.out.print("Enter command> ");
            String command = scanner.nextLine();

            commandManager.executeCommand(command);
        }
    }
}
