package org.example.commands;

import static org.example.utils.PersonIOService.INPUTS_LABELS;

import java.util.ArrayList;
import java.util.List;
import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

public class AddIfMax implements Command, ScriptCommand {
  private final CollectionManager collectionManager;
  private final PersonIOService personIOService;
  private final IOService ioService;
  public static final String DESCRIPTION =
      "add_if_max {element}: add a new element to a collection if its value is greater than the value of the largest element in that collection";

  public AddIfMax(
      CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
    this.collectionManager = collectionManager;
    this.personIOService = personIOService;
    this.ioService = ioService;
  }

  @Override
  public void execute() {
    ioService.print("Creating a new person to check max height...");
    Person p = personIOService.readPerson();
    checkAndAdd(p);
  }

  @Override
  public int scriptExecution(List<String> lines, int startIndex) {
    List<String> parts = new ArrayList<>();
    for (int j = startIndex + 1; j < lines.size() && parts.size() < INPUTS_LABELS.length; j++) {
      parts.add(lines.get(j).trim());
    }
    Person p = personIOService.buildPersonFromScript(parts.toArray(new String[0]));
    checkAndAdd(p);
    return startIndex + 1 + parts.size();
  }

  private void checkAndAdd(Person newPerson) {
    double currentMaxHeight = collectionManager.getMaxHeight();
    if (newPerson.getHeight() > currentMaxHeight) {
      collectionManager.add(newPerson);
      ioService.print(
          "ADDED: "
              + newPerson.getName()
              + " as his height: "
              + "("
              + newPerson.getHeight()
              + ")"
              + " > "
              + "max height ("
              + currentMaxHeight
              + ")");
    } else {
      ioService.print(
          newPerson.getName()
              + newPerson.getName()
              + "  NOT ADDED as his height: "
              + "("
              + newPerson.getHeight()
              + ")"
              + " ≤ "
              + "max height ("
              + currentMaxHeight
              + ")");
    }
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
