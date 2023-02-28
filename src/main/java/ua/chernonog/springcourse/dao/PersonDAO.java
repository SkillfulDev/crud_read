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
        people.add(new Person (++PERSON_COUNT,"Tom","Ray","tom@gmail.com"));
        people.add(new Person (++PERSON_COUNT,"Billy","McWema","Billy@gmail.com"));
        people.add(new Person (++PERSON_COUNT,"Anna","Solver","Anna@gmail.com"));
        people.add(new Person (++PERSON_COUNT,"Bobi","Mash>","Bobi@gmail.com"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(final int id){
        return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PERSON_COUNT);
        people.add(person);
    }

    public void update(int id,Person person) {
       people.get(id-1).setName(person.getName());
       people.get(id-1).setSurname(person.getSurname());
       people.get(id-1).setEmail(person.getEmail());
    }
}
