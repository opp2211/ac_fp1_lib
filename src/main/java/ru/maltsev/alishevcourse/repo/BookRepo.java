package ru.maltsev.alishevcourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maltsev.alishevcourse.model.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> { // тест измененного названия интерфейса
    List<Book> findAllByTitleStartingWith(String text);
}