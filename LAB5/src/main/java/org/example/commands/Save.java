package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.FileManager;

public class Save implements Command {
    private final CollectionManager collectionManager;
    public static final String DESCRIPTION = "save: save collection to file";

    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public void execute() {
        FileManager fileManager = new FileManager(collectionManager);
        String filePath = System.getenv("COLLECTION_FILE_PATH");
        if (filePath == null || filePath.isEmpty()) {
            filePath = "storage.xml";
        }
        fileManager.saveCollectionToXml(filePath);
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
