package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class MaxById implements Command {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  public static final String DESCRIPTION =
      "max_by_id: output any object from the collection whose id field value is maximum";

  public MaxById(CollectionManager collectionManager, IOService ioService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
  }

  @Override
  public void execute() {
    Person maxPerson = collectionManager.getMaxById();
    if (maxPerson != null) {
      ioService.print("Person with maximum ID:\n" + maxPerson);
    } else {
      ioService.print("Collection is empty.");
    }
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
