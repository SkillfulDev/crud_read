package ua.chernonog.springcourse.dao;

import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.chernonog.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(final int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> show (String email){
        return jdbcTemplate.query("SELECT * FROM person where email=?",new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) throws SQLException {
        jdbcTemplate.update("INSERT INTO person(name,email,age) VALUES(?,?,?)",
                person.getName(), person.getEmail(), person.getAge());

    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?,email=?,age=? WHERE id = ?", person.getName(), person.getEmail(),
                person.getAge(), id);

    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void requestWithBatch() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person VALUES(?,?,?,?)", person.getId(),
                    person.getName(), person.getEmail(), person.getAge());
        }
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }


    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "NewPerson" + i, "Email" + i + "@gmail.com", 18 + i));
        }
        return people;
    }

    public void requestWitoutBatch() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person(name,email,age) VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,people.get(i).getName());
                ps.setString(2,people.get(i).getEmail());
                ps.setInt(3,people.get(i).getAge());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();

        System.out.println(after - before);

    }
}
