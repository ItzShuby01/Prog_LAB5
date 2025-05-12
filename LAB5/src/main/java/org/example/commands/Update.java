package org.example.commands;

import static org.example.utils.PersonIOService.INPUTS_LABELS;

import java.util.ArrayList;
import java.util.List;
import org.example.collection.Person;
import org.example.utils.*;

public class Update implements Command, ScriptCommand {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  private final PersonIOService personIOService;
  public static final String DESCRIPTION =
      "update id {element}: update the value of the collection element whose id is equal to the given one";

  public Update(
      CollectionManager collectionManager, IOService ioService, PersonIOService personIOService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
    this.personIOService = personIOService;
  }

  @Override
  public void execute(String arg) {
    if (arg == null || arg.isBlank()) {
      ioService.print("Error: Missing ID or parameters");
      return;
    }
    String[] parts = arg.split("\\R");
    // if only ID provided -> interactive
    if (parts.length == 1) {
      try {
        int id = Integer.parseInt(parts[0].trim());
        Person p = collectionManager.getById(id);
        if (p == null) {
          ioService.print("Person with ID: " + id + " not found");
        } else {
          personIOService.updatePerson(p);
        }
      } catch (NumberFormatException ex) {
        ioService.print("Invalid ID format: " + parts[0]);
      }
      return;
    }

    // else script mode
    try {
      int id = Integer.parseInt(parts[0].trim());
      Person existing = collectionManager.getById(id);
      if (existing == null) {
        ioService.print("Person with ID: " + id + " not found");
        return;
      }
      String[] fields =
          parts.length > INPUTS_LABELS.length + 1
              ? java.util.Arrays.copyOfRange(parts, 1, INPUTS_LABELS.length + 1)
              : java.util.Arrays.copyOfRange(parts, 1, parts.length);
      personIOService.updatePersonFromScript(existing, fields);
    } catch (NumberFormatException e) {
      ioService.print("Invalid ID format: " + parts[0]);
    }
  }

  @Override
  public int scriptExecution(List<String> lines, int startIndex) {
    List<String> parts = new ArrayList<>();
    // include ID + up to 10 fields
    for (int j = startIndex; j < lines.size() && parts.size() < INPUTS_LABELS.length + 1; j++) {
      if (j == startIndex) {
        parts.add(lines.get(j).split(" ", 2)[1].trim());
      } else {
        parts.add(lines.get(j).trim());
      }
    }
    // delegate to execute(String)
    execute(String.join("\n", parts));
    return startIndex + parts.size();
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
