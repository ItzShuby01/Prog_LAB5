package org.example.commands;

import org.example.utils.CollectionManager;
import org.example.utils.IOService;

public class CountByLocation implements Command {
  private final CollectionManager collectionManager;
  private final IOService ioService;
  public static final String DESCRIPTION =
      "count_by_location location: output the number of elements whose location field value is equal to the specified one";

  public CountByLocation(CollectionManager collectionManager, IOService ioService) {
    this.collectionManager = collectionManager;
    this.ioService = ioService;
  }

  @Override
  public void execute(String arg) {
    int count = collectionManager.countByLocation(arg);
    ioService.print(
        "Number of persons with location '" + arg.toUpperCase().trim() + "' is " + count);
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
