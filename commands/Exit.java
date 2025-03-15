package org.example.commands;

public class Exit implements Command{

    @Override
    public void execute() {
        System.out.println("EXITING THE APP, SEE YOU AGAIN LATER !!!");
        System.exit(1);
    }

    @Override
    public void execute(String arg) {
    }
}
