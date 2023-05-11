package ru.khananov.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.khananov.model.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (fio, birth_date) VALUES (?, ?)",
                person.getFio(), person.getBirthDate());
    }

    public void update(Person updatePerson) {
        jdbcTemplate.update("UPDATE person SET (fio, birth_date) = (?, ?) WHERE id = ?",
                updatePerson.getFio(), updatePerson.getBirthDate(), updatePerson.getId());
    }

    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}
