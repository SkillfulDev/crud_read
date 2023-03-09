package ua.chernonog.springcourse.dao;

import org.postgresql.jdbc.PgConnection;
import org.springframework.stereotype.Component;
import ua.chernonog.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PERSON_COUNT;
    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";

    private static final String USERNAME = "postgres";

    private static final String PASSWORD = "pass";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        String SQL = "SELECT * FROM PERSON;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }


    public Person show(final int id) {
        return null;
    }

    public void save(Person person) {

    }

    public void update(int id, Person person) {
        Person personToBeUpdate = show(id);
        personToBeUpdate.setName(person.getName());
        personToBeUpdate.setEmail(person.getEmail());
        personToBeUpdate.setAge(person.getAge());

    }

    public void delete(int id) {
//        people.removeIf(p->p.getId()==id);
    }
}
