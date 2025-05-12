package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.CollectionManager.InfoData;
import org.example.utils.IOService;

public class Info implements Command {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  public static final String DESCRIPTION =
      "info: print collection information (type, initialization date, number of elements, etc.) to standard output";

  public Info(CollectionManager collectionManager, IOService ioService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
  }

  @Override
  public void execute() {
    InfoData info = collectionManager.getInfo();
    ioService.print("Collection Type: " + info.getCollectionType());
    ioService.print("Initialization Date: " + info.getInitializationDate());
    ioService.print("Number of persons in collection: " + info.getElementCount());
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
