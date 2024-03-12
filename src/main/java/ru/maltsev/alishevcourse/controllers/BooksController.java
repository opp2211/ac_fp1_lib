package ru.maltsev.alishevcourse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maltsev.alishevcourse.dao.BookDao;
import ru.maltsev.alishevcourse.dao.PersonDao;
import ru.maltsev.alishevcourse.model.Book;
import ru.maltsev.alishevcourse.model.Person;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    @GetMapping
    public String showAll(Model model) {
        List<Book> foundBooks = bookDao.getAll();
        model.addAttribute("books", foundBooks);
        return "books/index";
    }
    @GetMapping("/new")
    public String showAddForm(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String addNew(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookDao.addNew(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Book book = bookDao.getById(id);
        List<Person> people = personDao.getAll();
        Person holder = people.stream()
                .filter(person -> Objects.equals(person.getId(), book.getHolderId()))
                .findAny().orElse(null);

        model.addAttribute("book", book);
        model.addAttribute("people", people);
        model.addAttribute("holder", holder);

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setHolder")
    public String setHolder(@PathVariable("id") int bookId,
                            @RequestParam("holder_id") int holderId) {
        bookDao.setHolder(bookId, holderId);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/removeHolder")
    public String removeHolder(@PathVariable int id) {
        bookDao.removeHolder(id);

        return "redirect:/books/{id}";
    }
}
