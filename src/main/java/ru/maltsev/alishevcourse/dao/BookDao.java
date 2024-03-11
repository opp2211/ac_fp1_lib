package ru.maltsev.alishevcourse.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.maltsev.alishevcourse.model.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void addNew(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public Book getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
