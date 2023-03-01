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
        people.add(new Person (++PERSON_COUNT,"Tom","Ray","tom@gmail.com",34));
        people.add(new Person (++PERSON_COUNT,"Billy","McWema","Billy@gmail.com",38));
        people.add(new Person (++PERSON_COUNT,"Anna","Solver","Anna@gmail.com",23));
        people.add(new Person (++PERSON_COUNT,"Bobi","Mash>","Bobi@gmail.com",28));
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
      Person personToBeUpdate = show(id);
      personToBeUpdate.setName(person.getName());
      personToBeUpdate.setSurname(person.getSurname());
      personToBeUpdate.setEmail(person.getEmail());
      personToBeUpdate.setAge(person.getAge());

    }

    public void delete(int id) {
        people.removeIf(p->p.getId()==id);
    }
}
