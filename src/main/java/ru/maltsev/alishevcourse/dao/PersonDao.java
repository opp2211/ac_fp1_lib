package ru.maltsev.alishevcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maltsev.alishevcourse.model.Person;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void addNew(Person person) {
        jdbcTemplate.update("INSERT INTO person (fio, year_of_birth) VALUES (?, ?)",
                person.getFio(), person.getYearOfBirth());
    }

    public Person getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
    }

    public void update(int id, Person person) {
        person.setId(id);
        jdbcTemplate.update("UPDATE person SET fio = ?, year_of_birth = ? WHERE id = ?",
                person.getFio(), person.getYearOfBirth(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public Optional<Object> getByFio(String fio) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM person WHERE fio = ?",
                new Object[]{fio}, new BeanPropertyRowMapper<>(Person.class)));
    }
}
