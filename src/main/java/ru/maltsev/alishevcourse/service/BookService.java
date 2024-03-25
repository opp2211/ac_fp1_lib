package ru.maltsev.alishevcourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maltsev.alishevcourse.model.Book;
import ru.maltsev.alishevcourse.model.Person;
import ru.maltsev.alishevcourse.repo.BookRepo;
import ru.maltsev.alishevcourse.repo.PersonRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;
    private final PersonRepository personRepository;

    public List<Book> findAll(Integer page, Integer itemsPerPage, boolean sortByYear) {
        if (page == null || itemsPerPage == null) {
            page = 0;
            itemsPerPage = Integer.MAX_VALUE;
        }
        Sort sort = sortByYear ? Sort.by(Sort.Direction.ASC, "year") : Sort.unsorted();

        return bookRepo.findAll(PageRequest.of(page, itemsPerPage, sort)).toList();
    }

    public Book getById(int id) {
        return bookRepo.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Book ID = %d not found!", id)));
    }

    public List<Book> searchByTitle(String text) {
        return bookRepo.findAllByTitleStartingWith(text);
    }

    @Transactional
    public void addNew(Book book) {
        book.setId(null);
        bookRepo.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToUpdate = getById(id);

        book.setId(id);
        book.setHolder(bookToUpdate.getHolder());

        bookRepo.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepo.deleteById(id);
    }

    @Transactional
    public void setHolder(int bookId, int holderId) {
        Book book = getById(bookId);
        Person holder = personRepository.findById(holderId).orElseThrow(() ->
                new RuntimeException(String.format("Person ID = %d not found!", holderId)));
        book.setHolder(holder);
        book.setTakenAt(Date.from(Instant.now()));
    }

    @Transactional
    public void removeHolder(int id) {
        Book book = getById(id);
        book.setHolder(null);
        book.setTakenAt(null);
    }
}
