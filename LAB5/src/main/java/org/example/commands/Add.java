package org.example.commands;

import org.example.collection.*;
import org.example.utils.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Add implements Command {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;

    public Add(CollectionManager collectionManager, PersonIOService personIOService, IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
        this.ioService = ioService;



    @Override
    public void execute(String arg) {
        // 2) Script mode
        if (arg == null || arg.isEmpty()) {
            execute(); // Fallback to interactive mode
            return;
        }

        String[] parts = arg.split("\\n");
        if (parts.length != 10) {
            ioService.print("Error: Need exactly 10 lines of data");
            return;
        }

        try {
            // Validate all inputs
            for (int i = 0; i < 10; i++) {
                if (!inputValidation(i, parts[i], ioService)) {
                    ioService.print("Validation failed for in " + INPUTS_LABELS[i]);
                    return;
                }
            }

            // Parse and create Person
            Person person = parsePerson(parts);

            if (collectionManager.add(person)) {
                ioService.print("Added: " + person.getName());
            } else {
                ioService.print("Failed to add: " + person.getName());
            }

        } catch (NumberFormatException e) {
            ioService.print("Invalid number format");
        } catch (IllegalArgumentException e) {
            ioService.print("Invalid value: " + e.getMessage());
        }
    }

    private Person parsePerson(String[] parts) {
        return new Person(
                collectionManager.generateId(),
                parts[0].trim(),
                new Coordinates(
                        (int) Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2])
                ),
                LocalDateTime.now(),
                Double.parseDouble(parts[3]),
                EyeColor.valueOf(parts[4].trim().toUpperCase()),
                HairColor.valueOf(parts[5].trim().toUpperCase()),
                Country.valueOf(parts[6].trim().toUpperCase()),
                new Location(
                        Float.parseFloat(parts[7]),
                        Float.parseFloat(parts[8]),
                        parts[9].trim()
                )
        );
    }

    public static final String[] INPUTS_LABELS = {
            "name (string)",
            "x (float)",
            "y (double)",
            "height (double)",
            "eyeColor (valid : RED/BLACK/ORANGE)",
            "hairColor (valid: GREEN/BLUE/YELLOW/BROWN)",
            "nationality (valid: RUSSIA/GERMANY/ITALY/THAILAND/JAPAN)",
            "locationX (float)",
            "locationY (double)",
            "locationName (string)"
    };

    public static boolean inputValidation(int index, String value, IOService ioService) {
        try {
            switch (index) {
                case 0: // name
                    if (value.trim().isEmpty()) {
                        ioService.print("Name cannot be empty");
                        return false;
                    }
                    return true;
                case 1: // x
                    Float.parseFloat(value);
                    return true;
                case 2:  //y
                    Double.parseDouble(value);
                    return true;
                case 3:   // height
                    Double.parseDouble(value);
                    return true;
                case 4:  // eyeColor
                    return enumValidation(EyeColor.class, value, "Eye color", ioService);
                case 5:   // hairColor
                    return enumValidation(HairColor.class, value, "Hair color", ioService);
                case 6:    // nationality
                    return enumValidation(Country.class, value, "Nationality", ioService);
                case 7:   // locationX
                    Float.parseFloat(value);
                    return true;
                case 8:   // locationY
                    Float.parseFloat(value);
                    return true;
                case 9:   // locationName
                    if (value.trim().isEmpty()) {
                        ioService.print("Location name cannot be empty");
                        return false;
                    }
                    return true;
                default:
                    return false;
            }
        } catch (NumberFormatException e) {
            ioService.print("Invalid number format for " + INPUTS_LABELS[index]);
            return false;
        }
    }

    private static <T extends Enum<T>> boolean enumValidation(Class<T> enumClass,
                                                              String value,
                                                              String fieldName,
                                                              IOService ioService) {
        try {
            Enum.valueOf(enumClass, value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            ioService.print("Invalid " + fieldName + ": " + "'" + value + "'" +
                    ". Valid options: " + Arrays.toString(enumClass.getEnumConstants()));
            return false;
        }
    }
}
