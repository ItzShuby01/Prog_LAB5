package org.example.commands;

public interface Command {
    default void execute() {
    }

    default void execute(String arg) {
    }
}
