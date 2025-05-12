package org.example.utils;

import java.util.Scanner;

public class ConsoleManager {
  private final CommandManager commandManager;
  private boolean running = true;

  public ConsoleManager(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  public void interactiveMode() {
    System.out.println("WELCOME TO THE PERSON COLLECTION APP");
    System.out.println("Enter 'help' to see available commands, 'exit' to stop the program");

    try (Scanner scanner = new Scanner(System.in)) {
      while (running) {
        System.out.print("Enter command> ");
        String command = scanner.nextLine().trim();
        if (!command.isEmpty()) {
          commandManager.executeCommand(command);
        }
        if (commandManager.isExitRequested()) {
          running = false;
        }
      }
    }
  }
}
