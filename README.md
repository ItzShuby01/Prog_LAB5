
This labwork is a Java Console application for managing a collection of elements (Mainly 'Person') . It provides various commands to manipulate and query the collection.

## Features & Usage

The application supports the following commands:

- `help`: Display help on available commands
- `info`: Print collection information
- `show`: Print all elements of the collection
- `add {element}`: Add a new item to the collection
- `update id {element}`: Update the value of the collection element with the given id
- `remove_by_id id`: Remove an element by its id
- `clear`: Clear the collection
- `save`: Save the collection to a file
- `execute_script file_name`: Execute commands from a script file
- `exit`: Exit the program
- `add_if_max {element}`: Add a new element if its value is greater than the largest element
- `remove_lower {element}`: Remove elements less than the specified value
- `history`: Print the last 7 commands
- `average_of_height`: Output the average height of elements
- `max_by_id`: Output the element with the maximum id
- `count_by_location location`: Count elements with the specified location

## Running the Application

1. Setting the environment variable `COLLECTION_FILE_PATH` to the path of the collection file:
    ```sh
    export COLLECTION_FILE_PATH=storage.xml
    ```

2. Running the application the application:
    ```sh
    java -jar target/java5-1.0-SNAPSHOT.jar
    ```
