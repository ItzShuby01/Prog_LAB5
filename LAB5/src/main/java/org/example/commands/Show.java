package org.example.commands;

import java.util.List;
import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class Show implements Command {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  public static final String DESCRIPTION =
      "show: print all elements of the collection to standard output";

  public Show(CollectionManager collectionManager, IOService ioService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
  }

  @Override
  public void execute() {
    List<Person> persons = collectionManager.getAllPersons();
    if (persons.isEmpty()) {
      ioService.print("THE COLLECTION IS EMPTY");
      return;
    }

    ioService.print("DISPLAYING THE COLLECTION DATA...");
    for (Person person : persons) {
      ioService.print(person.toString());
      ioService.print("");
    }
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
