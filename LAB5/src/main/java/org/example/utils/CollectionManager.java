package org.example.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.example.collection.*;

public class CollectionManager {
  private final Set<Person> personTreeSet = new TreeSet<>();
  private final String collectionFilePath;

  public CollectionManager(String collectionFilePath) {
    this.collectionFilePath = collectionFilePath;
  }

  // Set the collection
  public void setPersonTreeSet(TreeSet<Person> loadedCollection) {
    if (loadedCollection != null) {
      personTreeSet.clear();
      personTreeSet.addAll(loadedCollection);
    }
  }

  // Retrieve all 'Person' objects as a list
  public List<Person> getAllPersons() {
    return new ArrayList<>(personTreeSet);
  }

  // Automatically generates ID
  public int generateId() {
    Random random = new Random();
    int id;
    Set<Integer> existingIds =
        personTreeSet.stream().map(Person::getId).collect(Collectors.toSet());
    do {
      id = random.nextInt(Integer.MAX_VALUE) + 1;
    } while (existingIds.contains(id));
    return id;
  }

  // Method for adding person to the collection
  public boolean add(Person person) {
    return personTreeSet.add(person);
  }

  // Helper method for 'REMOVE_BY_ID'
  public void removePerson(Person person) {
    personTreeSet.remove(person);
  }

  // Helper method for 'CLEAR'
  public void clear() {
    ArrayList<Person> personList = new ArrayList<>(personTreeSet);
    personList.forEach(personTreeSet::remove);
  }

  // Get the person with the maximum ID
  public Person getMaxById() {
    if (personTreeSet.isEmpty()) return null;
    return ((TreeSet<Person>) personTreeSet).last(); // Cast to TreeSet
  }

  public double getAverageHeight() {
    if (personTreeSet.isEmpty()) {
      throw new IllegalStateException("Collection is empty");
    }
    return personTreeSet.stream().mapToDouble(Person::getHeight).average().orElse(0.0);
  }

  // Helper for 'COUNT_BY_LOCATION'
  public int countByLocation(String locationName) {
    String trimmedLocationName = locationName.trim();
    int count = 0;
    for (Person person : personTreeSet) {
      if (person.getLocation().getName().equalsIgnoreCase(trimmedLocationName)) {
        count++;
      }
    }
    return count;
  }

  public Person getById(int id) {
    return personTreeSet.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
  }

  public static class InfoData {
    private final String collectionType;
    private final String initializationDate;
    private final int elementCount;

    public InfoData(String collectionType, String initializationDate, int elementCount) {
      this.collectionType = collectionType;
      this.initializationDate = initializationDate;
      this.elementCount = elementCount;
    }

    // Getters
    public String getCollectionType() {
      return collectionType;
    }

    public String getInitializationDate() {
      return initializationDate;
    }

    public int getElementCount() {
      return elementCount;
    }
  }

  public InfoData getInfo() {
    return new InfoData(
        personTreeSet.getClass().getName(), getInitializationDate(), personTreeSet.size());
  }

  // Helper for 'INFO' to get the initialization date from the storage file
  private String getInitializationDate() {
    String date;
    if (collectionFilePath == null) {
      return "Initialization date not available because the environmental variable is not set";
    }
    try {
      BasicFileAttributes attributes =
          Files.readAttributes(Path.of(collectionFilePath), BasicFileAttributes.class);
      FileTime fileTime = attributes.creationTime();
      Instant instant = fileTime.toInstant();
      LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd.MM.yyyy HH:mm:ss");
      date = localDateTime.format(formatter);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file " + e.getMessage());
    }
    return date;
  }

  // Helper for 'REMOVE_LOWER'

  public List<Person> removeLower(Person threshold) {
    Set<Person> lower = new TreeSet<>(((TreeSet<Person>) personTreeSet).headSet(threshold)); // copy
    personTreeSet.removeAll(lower);
    return new ArrayList<>(lower);
  }

  // Get the maximum height of persons in the collection
  public double getMaxHeight() {
    return personTreeSet.stream().mapToDouble(Person::getHeight).max().orElse(0.0);
  }
}
