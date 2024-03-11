package ru.maltsev.alishevcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maltsev.alishevcourse.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }
}
