package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class AverageOfHeight implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;
    public static final String DESCRIPTION =  "average_of_height: output the average height field value for all elements in a collection";


    public AverageOfHeight(CollectionManager collectionManager, IOService ioService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
    }

    @Override
    public void execute() {
        try {
            double average = collectionManager.getAverageHeight();
            ioService.print(String.format("Average height: %.2f", average));
        } catch (IllegalStateException e) {
            ioService.print(e.getMessage());
        }
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
