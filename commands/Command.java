package org.example.commannds;

public interface Command {
    void execute(); 
    
    default void execute(String arg) {
        throw new UnsupportedOperationException("This command does not support arguments.");
    }
}
