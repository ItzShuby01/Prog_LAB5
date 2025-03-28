package org.example.collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "persons")
public class PersonList {
    private List<Person> persons;

    // No-arg constructor required by JAXB
    public PersonList() {}

    public PersonList(List<Person> persons) {
        this.persons = (persons != null) ? persons : new ArrayList<>();
    }

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
