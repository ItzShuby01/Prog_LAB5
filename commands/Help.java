package org.example.commands;


public class Help implements Command{
    @Override
    public void execute() {
        String manual = """
                help: display help on available commands
                info: print collection information (type, initialization date, number of elements, etc.) to standard output.
                show: print all elements of the collection to standard output in string representation
                add {element}: add a new item to the collection
                update id {element}: update the value of the collection element whose id is equal to the given one
                remove_by_id id: remove an element from a collection by its id
                clear: clear collection
                save: save collection to file
                execute_script file_name: read and execute the script from the specified file. The script contains commands in the same form in which the user enters them in interactive mode.
                exit: exit the program (without saving to file)
                add_if_max {element}: add a new element to a collection if its value is greater than the value of the largest element in that collection
                remove_lower {element}: remove all elements from the collection that are less than the specified number
                history: print the last 7 commands (without their arguments)
                average_of_height: output the average height field value for all elements in a collection
                max_by_id: output any object from the collection whose id field value is maximum
                count_by_location location: output the number of elements whose location field value is equal to the specified one""";

        System.out.println(manual);
    }

    @Override
    public void execute(String arg) {

    }
}
