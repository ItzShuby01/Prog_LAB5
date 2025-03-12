
This labwork is a Java Console application for managing a collection of elements (Mainly 'Person') . It provides various commands to manipulate and query the collection.

## Features

- Display help on available commands
- Print collection information
- Show all elements of the collection
- Add, update, and remove elements
- Clear the collection
- Save the collection to a file
- Execute scripts containing commands
- Exit the program
- Add elements conditionally
- Remove elements based on conditions
- Print command history
- Calculate average values
- Find elements with maximum values
- Count elements based on specific fields

## Requirements

- Java 15 or higher
- Maven

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/your-repo-name.git
    cd your-repo-name
    ```

2. Set up environment variables:
    Create a file named `variables.env` with the following content:
    ```dotenv
    STORAGE=storage.xml
    SCRIPT=script.txt
    ```

3. Build the project using Maven:
    ```sh
    mvn clean package
    ```

## Running the Application

1. Set the environment variable `COLLECTION_FILE_PATH` to the path of your collection file:
    ```sh
    export COLLECTION_FILE_PATH=path/to/your/storage.xml
    ```

2. Run the application:
    ```sh
    java -jar target/java5-1.0-SNAPSHOT.jar
    ```

## Usage

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

## NOTE
Replace `yourusername` and `your-repo-name` with your actual GitHub username and repository name. Adjust the paths and commands as necessary for your specific setup.
