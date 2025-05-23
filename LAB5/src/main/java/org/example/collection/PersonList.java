package org.example.collection;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "persons")
public class PersonList {
  private List<Person> persons;

  // No-arg constructor required by JAXB
  public PersonList() {}

  public PersonList(List<Person> persons) {
    this.persons = persons;
  }

  @XmlElement(name = "person")
  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }
}
