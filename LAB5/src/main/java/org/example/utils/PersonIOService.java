package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

import org.example.collection.*;

public class PersonIOService {
  private final IOService ioService;
  private final CollectionManager collectionManager;

  public PersonIOService(IOService ioService, CollectionManager collectionManager) {
    this.ioService = ioService;
    this.collectionManager = collectionManager;
  }

  public Person readPerson() {
    int id = collectionManager.generateId();
    ioService.print("ID automatically generated: " + id);
    String name = ioService.readLine("Enter person name: ");
    Coordinates coordinates = readCoordinate();
    LocalDateTime creationDate = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
    ioService.print("Creation date was automatically generated: " + creationDate.format(formatter));
    double height = readHeight();
    EyeColor eyeColor = readEyeColor();
    HairColor hairColor = readHairColor();
    Country nationality = readCountry();
    Location location = readLocation();
    return new Person(
            id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
  }

  private Coordinates readCoordinate() {
    Coordinates coordinates = new Coordinates();
    boolean validInput = false;
    while (!validInput) {
      try {
        ioService.print("Enter person coordinates");
        int x = ioService.readInt("x: ");
        if (x > 629) {
          throw new Exception("x must be less than 630");
        }
        double y = ioService.readDouble("y: ");
        if (Double.isNaN(y) || Double.isInfinite(y)) {
          throw new Exception("Y coordinate cannot be NaN or Infinity");
        }

        coordinates.setX(x);
        coordinates.setY(y);
        validInput = true;
      } catch (Exception e) {
        ioService.print(e.getMessage());
        ioService.print("Invalid formats. x must be an integer < 630 , and y - a finite double.");
      }
    }
    return coordinates;
  }

  private double readHeight() {
    while (true) {
      try {
        double height = ioService.readDouble("Enter person height: ");
        if (Double.isNaN(height) || Double.isInfinite(height) || height <= 0) {
          ioService.print("Height must be positive and finite (no NaN/Infinity)");
          continue;
        }
        return height;
      } catch (Exception e) {
        ioService.print(e.getMessage());
      }
    }
  }

  private EyeColor readEyeColor() {
    while (true) {
      try {
        String input = ioService.readLine("Enter person eye color: ").toUpperCase();
        return EyeColor.valueOf(input);
      } catch (IllegalArgumentException e) {
        ioService.print("Invalid input. Valid eye colors are RED, BLACK, and ORANGE.");
      }
    }
  }

  private HairColor readHairColor() {
    while (true) {
      try {
        String input = ioService.readLine("Enter person hair color: ").toUpperCase();
        return HairColor.valueOf(input);
      } catch (IllegalArgumentException e) {
        ioService.print("Invalid input. Valid hair colors are GREEN, BLUE, YELLOW, and BROWN.");
      }
    }
  }

  private Country readCountry() {
    while (true) {
      try {
        String input = ioService.readLine("Enter nationality (press Enter to skip): ").trim();
        if (input.isBlank()) {
          return null; // Allow null for nationality
        }
        return Country.valueOf(input.toUpperCase());
      } catch (IllegalArgumentException e) {
        ioService.print(
                "Invalid input. Valid options are RUSSIA, GERMANY, ITALY, THAILAND, and JAPAN.");
      }
    }
  }

  private Location readLocation() {
    while (true) {
      try {
        String input = ioService.readLine("Do you want to enter a location? (y/n): ").trim();
        if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
          return null; // Allow null for location
        } else if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
    Location location = new Location();
    boolean validInput = false;
    while (!validInput) {
      try {
        ioService.print("Enter person location");
        float parsedLocationX = (float) ioService.readDouble("x: ");
        if (Float.isNaN(parsedLocationX) || Float.isInfinite(parsedLocationX)){
          throw new Exception("Location X cannot be NaN/Infinity");
        }

        float parsedLocationY = (float) ioService.readDouble("y: ");
        if (Float.isNaN(parsedLocationY) || Float.isInfinite(parsedLocationY)){
          throw new Exception("Location Y cannot be NaN/Infinity");
        }
        String name = ioService.readLine("name: ");
        if (name.length() > 530) {
          throw new Exception("Location name cannot exceed 530 characters");
        }
        location.setX(parsedLocationX);
        location.setY(parsedLocationY);
        location.setName(name);
        validInput = true;
      } catch (Exception e) {
        ioService.print("Error: " + e.getMessage());
      }
    }
    return location;
        } else {
          ioService.print("Please answer with 'y', 'yes', 'n', or 'no'.");
        }
      } catch (Exception e) {
        ioService.print("Error: " + e.getMessage());
      }
    }
  }

  public static final String[] INPUTS_LABELS = {
          "name (string)",
          "x (int)",
          "y (double)",
          "height (double)",
          "eyeColor (valid : RED/BLACK/ORANGE)",
          "hairColor (valid: GREEN/BLUE/YELLOW/BROWN)",
          "nationality (valid: RUSSIA/GERMANY/ITALY/THAILAND/JAPAN)",
          "locationX (float)",
          "locationY (float)",
          "locationName (string)"
  };

  private static <T extends Enum<T>> boolean enumValidation(
          Class<T> enumClass, String value, String fieldName, IOService ioService) {
    try {
      Enum.valueOf(enumClass, value.toUpperCase());
      return true;
    } catch (IllegalArgumentException e) {
      ioService.print(
              "Invalid "
                      + fieldName
                      + ": "
                      + "'"
                      + value
                      + "'"
                      + ". Valid options: "
                      + Arrays.toString(enumClass.getEnumConstants()));
      return false;
    }
  }

  public static boolean inputValidation(int index, String value, IOService ioService) {
    if (value == null) {
      ioService.print(INPUTS_LABELS[index] + " is missing");
      return false;
    }
    try {
      return switch (index) {
        case 0 -> {
          if (value.isBlank()) {
            ioService.print("Name cannot be empty");
            yield false;
          }
          yield true;
        }
        case 1 -> {
          int parsedX = Integer.parseInt(value);
          if (parsedX > 630) {
            ioService.print("X cannot be greater than 630");
            yield false;
          }
          yield true;
        }

        case 2 -> {
          double parsedY = Double.parseDouble(value);
          if (Double.isNaN(parsedY) || Double.isInfinite(parsedY)) {
            ioService.print("Y coordinate cannot be NaN or Infinity");
            yield false;
          }
          yield true;
        }
        case 3 -> {
          double parsedHeight = Double.parseDouble(value);
          if (parsedHeight <= 0 || Double.isNaN(parsedHeight) || Double.isInfinite(parsedHeight)) {
            ioService.print("Height must be positive and finite");
            yield false;
          }
          yield true;
        }
        case 4 -> enumValidation(EyeColor.class, value, "Eye color", ioService);
        case 5 -> enumValidation(HairColor.class, value, "Hair color", ioService);
        case 6 -> {
          if (value.trim().isEmpty()) {
            yield true; // Allow empty input for nationality
          }
          enumValidation(Country.class, value, "Nationality", ioService);
          yield true;
        }
          case 7 -> {
            if (value.trim().isEmpty()) {
              yield true; // Allow empty input for location coordinate X
            }
          float parsedLocationX = Float.parseFloat(value);
          if (Float.isNaN(parsedLocationX) || Float.isInfinite(parsedLocationX)) {
            ioService.print("X coordinate cannot be NaN or Infinity");
            yield false;
          }
          yield true;
        }
        case 8 -> {
          if (value.trim().isEmpty()) {
            yield true; // Allow empty input for location coordinate Y
          }
          float parsedLocationY = Float.parseFloat(value);
          if (Float.isNaN(parsedLocationY) || Float.isInfinite(parsedLocationY)) {
            ioService.print("X coordinate cannot be NaN or Infinity");
            yield false;
          }
          yield true;
        }
        case 9 -> {
          if (value.trim().isEmpty()) {
            yield true; // Allow empty input for location  name
          }
          if (value.isBlank()) {
            ioService.print("Location name cannot be empty");
            yield false;
          }
          yield true;
        }
        default -> false;
      };
    } catch (NumberFormatException e) {
      ioService.print("Invalid number format for " + INPUTS_LABELS[index]);
      return false;
    }
  }

  private Person readPersonFromScript(String[] parts, Person target) {
    final int F = INPUTS_LABELS.length;
    String[] all = new String[F];
    for (int i = 0; i < F; i++) {
      all[i] = (i < parts.length && parts[i] != null) ? parts[i].trim() : null;
    }
    int firstBad = -1;
    for (int i = 0; i < F; i++) {
      if (all[i] == null || !inputValidation(i, all[i], ioService)) {
        firstBad = i;
        break;
      }
    }

    String name = all[0];
    Coordinates coordinates = null;
    double height = 0;
    EyeColor eyeColor = null;
    HairColor hairColor = null;
    Country nationality = null;
    Location location = null;

    if (firstBad == -1) {
      coordinates = new Coordinates(
              validateInt(Integer.parseInt(all[1])),
              validateDouble(Double.parseDouble(all[2]), "Coordinates Y"));
      height = validateDouble(Double.parseDouble(all[3]), "Height");
      eyeColor = EyeColor.valueOf(all[4].toUpperCase());
      hairColor = HairColor.valueOf(all[5].toUpperCase());
      nationality = all[6] == null || all[6].trim().isEmpty() ? null : Country.valueOf(all[6].toUpperCase());
      location = all[7] == null || all[8] == null || all[9] == null ||
              Stream.of(all[7], all[8], all[9]).anyMatch(String::isBlank)
              ? null
              : new Location(
              validateFloat(Float.parseFloat(all[7]), "Location X"),
              validateFloat(Float.parseFloat(all[8]), "Location Y"),
              validateString(all[9], "Location Name"));
    } else {
      // Handle partial failures by falling back to interactive input
      if (firstBad > 0) name = all[0];
      if (firstBad > 2)
        coordinates = new Coordinates(
                validateInt(Integer.parseInt(all[1])),
                validateDouble(Double.parseDouble(all[2]), "Coordinates Y"));
      if (firstBad > 3) height = validateDouble(Double.parseDouble(all[3]), "Height");
      if (firstBad > 4) eyeColor = EyeColor.valueOf(all[4].toUpperCase());
      if (firstBad > 5) hairColor = HairColor.valueOf(all[5].toUpperCase());
      if (firstBad > 6)
        nationality = all[6] == null || all[6].trim().isEmpty() ? null : Country.valueOf(all[6].toUpperCase());
      if (firstBad == 9)
        location = all[7] == null || all[8] == null || all[9] == null ||
                Stream.of(all[7], all[8], all[9]).anyMatch(String::isBlank)
                ? null
                : new Location(
                validateFloat(Float.parseFloat(all[7]), "Location X"),
                validateFloat(Float.parseFloat(all[8]), "Location Y"),
                validateString(all[9], "Location Name"));

      for (int i = firstBad; i < F; i++) {
        switch (i) {
          case 0 -> name = ioService.readLine("Enter " + INPUTS_LABELS[0] + ": ");
          case 1, 2 -> {
            coordinates = readCoordinate();
            i = 2;
          }
          case 3 -> height = readHeight();
          case 4 -> eyeColor = readEyeColor();
          case 5 -> hairColor = readHairColor();
          case 6 -> nationality = readCountry(); // Can be null
          case 7, 8, 9 -> {
            location = readLocation(); // Can be null
            i = 9;
          }
        }
      }
    }

    // Create or update the person object
    if (target == null) {
      return new Person(
              collectionManager.generateId(),
              name,
              coordinates,
              LocalDateTime.now(),
              height,
              eyeColor,
              hairColor,
              nationality,
              location);
    } else {
      target.setName(name);
      target.setCoordinates(coordinates);
      target.setHeight(height);
      target.setEyeColor(eyeColor);
      target.setHairColor(hairColor);
      target.setNationality(nationality);
      target.setLocation(location);
      return target;
    }
  }
  private double validateDouble(double value, String fieldName) {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException(fieldName + " cannot be NaN or Infinity");
    }
    return value;
  }

  private float validateFloat(float value, String fieldName) {
    if (Float.isNaN(value) || Float.isInfinite(value)) {
      throw new IllegalArgumentException(fieldName + " cannot be NaN or Infinity");
    }
    return value;
  }

  private int validateInt(int value) {
    // Add any integer-specific validation if needed
    return value;
  }

  private String validateString(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be empty");
    }
    return value;
  }

  public Person buildPersonFromScript(String[] parts) {
    return readPersonFromScript(parts, null);
  }

  public void updatePerson(Person person) {
    String name = ioService.readLine("Enter name: ");
    Coordinates coordinates = readCoordinate();
    ioService.print("Creation date cannot be changed");
    double height = readHeight();
    EyeColor eyeColor = readEyeColor();
    HairColor hairColor = readHairColor();
    Country nationality = readCountry();
    Location location = readLocation();
    person.setName(name);
    person.setCoordinates(coordinates);
    person.setHeight(height);
    person.setEyeColor(eyeColor);
    person.setHairColor(hairColor);
    person.setNationality(nationality);
    person.setLocation(location);
    ioService.print("Person with id " + person.getId() + " has been updated");
  }

  public void updatePersonFromScript(Person person, String[] parts) {
    readPersonFromScript(parts, person);
    ioService.print("Person with id " + person.getId() + " has been updated");
  }
}