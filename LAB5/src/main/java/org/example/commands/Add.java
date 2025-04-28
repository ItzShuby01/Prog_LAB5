package org.example.commands;

import org.example.collection.Person;
import org.example.utils.CollectionManager;
import org.example.utils.IOService;
import org.example.utils.PersonIOService;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.PersonIOService.INPUTS_LABELS;

public class Add implements Command, ScriptCommand {
    private final CollectionManager collectionManager;
    private final PersonIOService personIOService;
    private final IOService ioService;
    public static final String DESCRIPTION = "add {element}: add a new item to the collection";

    public Add(CollectionManager collectionManager,
               PersonIOService personIOService,
               IOService ioService) {
        this.collectionManager = collectionManager;
        this.personIOService = personIOService;
        this.ioService = ioService;
    }

    // Interactive mode
    @Override
    public void execute() {
        Person p = personIOService.readPerson();
        if (collectionManager.add(p))
            ioService.print(p.getName() + " added to collection");
        else
            ioService.print("Failed to add person");
    }



    // Script mode
    @Override
    public int scriptExecution(List<String> lines, int startIndex) {
        List<String> parts = new ArrayList<>();
        for (int j = startIndex + 1;
             j < lines.size() && parts.size() < INPUTS_LABELS.length;
             j++) {
            parts.add(lines.get(j).trim());
        }
        Person p = personIOService.buildPersonFromScript(parts.toArray(new String[0]));
        if (collectionManager.add(p))
            ioService.print("Added: " + p.getName());
        else
            ioService.print("Failed to add: " + p.getName());
        return startIndex + 1 + parts.size();
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

