package org.example.utils;

import java.util.Scanner;

public class ConsoleIOService implements IOService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readLine(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid integer.");
            }
        }
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid double.");
            }
        }
    }
}
