package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.FileManager;

public class Save implements Commands{
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public void execute() {
        FileManager fileManager = new FileManager(collectionManager);
        String filePath = "storage.xml";
        fileManager.saveCollectionToXml(filePath);
    }

    @Override
    public void execute(String arg) {

    }
}
