package org.example.commands;

import org.example.collection.*;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Update implements Command {
    private final CollectionManager collectionManager;
    private final IOService ioService;
    private final PersonIOService personIOService;

    public Update(CollectionManager collectionManager, IOService ioService, PersonIOService personIOService) {
        this.collectionManager = collectionManager;
        this.ioService = ioService;
        this.personIOService = personIOService;
    }

    @Override
    public void execute(String arg) {
        if (arg == null || arg.trim().isEmpty()) {
            ioService.print("Error: Missing ID or parameters for update command");
            return;
        }

        // Interactive mode
        if (!arg.contains("\n")) {
            try {
                int id = Integer.parseInt(arg.trim());
                Person existing = collectionManager.getById(id);
                if (existing == null) {
                    ioService.print("Person with ID " + id + " not found");
                    return;
                }
                // Trigger interactive update
                personIOService.updatePerson(existing);
                return;
            } catch (NumberFormatException e) {
                ioService.print("Invalid ID format: " + arg);
                return;
            }
        }


        // Script mode : Handle multiple lines of input
        String[] parts = arg.split("\\n");
        if (parts.length != 11) {
            ioService.print("Error: Need exactly 11 lines of data (ID + 10 parameters)");
            return;
        }

        String idString = parts[0];
        String[] inputs = Arrays.copyOfRange(parts, 1, 11);

        try {
            int id = Integer.parseInt(idString);
            Person existing = collectionManager.getById(id);

            if (existing == null) {
                ioService.print("Person with ID " + id + " not found");
                return;
            }

            // Validate all inputs
            for (int i = 0; i < 10; i++) {
                if (!Add.inputValidation(i, inputs[i], ioService)) {
                    ioService.print("Validation failed for " + Add.INPUTS_LABELS[i]);
                    return;
                }
            }

            Person updated = parsePerson(inputs);

            // Update the existing person's fields
            existing.setName(updated.getName());
            existing.setCoordinates(updated.getCoordinates());
            existing.setHeight(updated.getHeight());
            existing.setEyeColor(updated.getEyeColor());
            existing.setHairColor(updated.getHairColor());
            existing.setNationality(updated.getNationality());
            existing.setLocation(updated.getLocation());
            existing.setCreationDate(LocalDateTime.now());

            ioService.print("Updated person ID " + id + ": " + existing.getName());

        } catch (NumberFormatException e) {
            ioService.print("Invalid ID format: " + idString);
        }
    }

    private Person parsePerson(String[] parts) {
        return new Person(
                0, // ID is ignored (we use existing inputted ID)
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
}
