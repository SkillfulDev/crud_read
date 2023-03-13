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
        String SQL = "SELECT * FROM Person;";
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
        Person person = null;
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM person WHERE id =?");
            pstm.setInt(1, id);
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();
            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(1,?,?,?)");
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getEmail());
        preparedStatement.setInt(3, person.getAge());

        preparedStatement.executeUpdate();

    }

    public void update(int id, Person person) {

        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE person SET name=?, email =?, age=? WHERE id=?");

            pstm.setString(1, person.getName());
            pstm.setString(2, person.getEmail());
            pstm.setInt(3, person.getAge());
            pstm.setInt(4, id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM person WHERE id =?");
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
