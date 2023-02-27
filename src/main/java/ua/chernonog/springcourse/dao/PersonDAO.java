package ua.chernonog.springcourse.dao;

import org.springframework.stereotype.Component;
import ua.chernonog.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class  PersonDAO {
    private List<Person> people;
    private static int PERSON_COUNT;

    {
        people = new ArrayList<>();
        people.add(new Person (++PERSON_COUNT,"Tom"));
        people.add(new Person (++PERSON_COUNT,"Billy"));
        people.add(new Person (++PERSON_COUNT,"Anna"));
        people.add(new Person (++PERSON_COUNT,"Bobi"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(final int id){
        return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    }
}
