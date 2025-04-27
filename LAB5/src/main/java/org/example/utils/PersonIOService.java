package org.example.utils;

import org.example.collection.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


//This class solely deals with user I/O
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
        Location location =readLocation();

        return new Person(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
    }

    private Coordinates readCoordinate() {
        Coordinates coordinates = new Coordinates();
        boolean validInput = false;
        while (!validInput) {
            try {
                ioService.print("Enter person coordinates");
                int x = ioService.readInt("x: ");
                if (x > 629) {
                    throw new Exception("x exceeds limits");
                }
                double y = ioService.readDouble("y: ");
                coordinates.setX(x);
                coordinates.setY(y);
                validInput = true;
            } catch (Exception e) {
                ioService.print(e.getMessage());
                ioService.print("Invalid formats. x must be an integer less than 630 and y a double.");
            }
        }
        return coordinates;
    }

    private double readHeight() {
        while (true) {
            try {
                double height = ioService.readDouble("Enter person height: ");
                if (height <= 0) {
                    ioService.print("Height must be positive.");
                    continue;
                }
                return height;
            } catch (Exception e) {
                ioService.print("Invalid input. Height must be a positive number.");
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
                String input = ioService.readLine("Enter nationality: ").toUpperCase();
                return Country.valueOf(input);
            } catch (IllegalArgumentException e) {
                ioService.print("Invalid input. Valid options are RUSSIA, GERMANY, ITALY, THAILAND, and JAPAN.");
            }
        }
    }

    private Location readLocation() {
        Location location = new Location();
        boolean validInput = false;
        while (!validInput) {
            try {
                ioService.print("Enter person location");
                float x = (float) ioService.readDouble("x: ");
                float y = (float) ioService.readDouble("y: ");
                String name = ioService.readLine("name: ");
                if (name.length() > 530) {
                    throw new Exception("Length of name too long");
                }
                location.setX(x);
                location.setY(y);
                location.setName(name);
                validInput = true;
            } catch (Exception e) {
                ioService.print("Invalid formats. 'x' and 'y' must be floats and 'name' a string.");
            }
        }
        return location;
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
                    Integer.parseInt(value);
                    yield true;
                }
                case 2 -> {
                    Double.parseDouble(value);
                    yield true;
                }
                case 3 -> {
                    double h = Double.parseDouble(value);
                    if (h <= 0) {
                        ioService.print("Height must be positive");
                        yield false;
                    }
                    yield true;
                }
                case 4 -> enumValidation(EyeColor.class, value, "Eye color", ioService);
                case 5 -> enumValidation(HairColor.class, value, "Hair color", ioService);
                case 6 -> enumValidation(Country.class, value, "Nationality", ioService);
                case 7, 8 -> {
                    Float.parseFloat(value);
                    yield true;
                }
                case 9 -> {
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
        } catch (IllegalArgumentException e) {
            // enumValidation already printed the message
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
            coordinates = new Coordinates((int) Double.parseDouble(all[1]), Double.parseDouble(all[2]));
            height = Double.parseDouble(all[3]);
            eyeColor = EyeColor.valueOf(all[4].toUpperCase());
            hairColor = HairColor.valueOf(all[5].toUpperCase());
            nationality = Country.valueOf(all[6].toUpperCase());
            location = new Location(Float.parseFloat(all[7]), Float.parseFloat(all[8]), all[9]);
        } else {
            if (firstBad > 0) name = all[0];
            if (firstBad > 2) coordinates = new Coordinates(Integer.parseInt(all[1]), Double.parseDouble(all[2]));
            if (firstBad > 3) height = Double.parseDouble(all[3]);
            if (firstBad > 4) eyeColor = EyeColor.valueOf(all[4].toUpperCase());
            if (firstBad > 5) hairColor = HairColor.valueOf(all[5].toUpperCase());
            if (firstBad > 6) nationality = Country.valueOf(all[6].toUpperCase());
            if (firstBad == 9) location = new Location(Float.parseFloat(all[7]), Float.parseFloat(all[8]), all[9]);

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
                    case 6 -> nationality = readCountry();
                    case 7, 8, 9 -> {
                        location = readLocation();
                        i = 9;
                    }
                }
            }
        }

        if (target == null) {
            return new Person(
                    collectionManager.generateId(),
                    name, coordinates, LocalDateTime.now(),
                    height, eyeColor, hairColor, nationality, location
            );
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