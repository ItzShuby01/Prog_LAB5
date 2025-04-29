package org.example.commands;

import java.util.List;
import org.example.collection.*;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

public class RemoveLower implements Command {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  private final PersonIOService personIOService;
  public static final String DESCRIPTION =
      "remove_lower {element}: remove all elements from the collection that are less than the specified number";

  public RemoveLower(
      CollectionManager collectionManager, IOService ioService, PersonIOService personIOService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
    this.personIOService = personIOService;
  }

  @Override
  public void execute() {
    try {
      Person threshold = personIOService.readPerson();
      List<Person> removedPersons = collectionManager.removeLower(threshold);
      ioService.print("Removed " + removedPersons.size() + " persons");
    } catch (Exception e) {
      ioService.print("Error: " + e.getMessage());
    }
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
