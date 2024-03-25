package ru.maltsev.alishevcourse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maltsev.alishevcourse.model.Book;
import ru.maltsev.alishevcourse.model.Person;
import ru.maltsev.alishevcourse.service.BookService;
import ru.maltsev.alishevcourse.service.PersonService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {
    private final BookService bookService;
    private final PersonService personService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sortByYear,
                        @RequestParam(value = "page", required = false) Integer pageNum,
                        @RequestParam(value = "items_per_page", required = false) Integer itemsPerPage) {
        List<Book> foundBooks = bookService.findAll(pageNum, itemsPerPage, sortByYear);
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
        bookService.addNew(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Book book = bookService.getById(id);
        List<Person> people = personService.findAll();
        Person holder = book.getHolder();

        model.addAttribute("book", book);
        model.addAttribute("people", people);
        model.addAttribute("holder", holder);

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setHolder")
    public String setHolder(@PathVariable("id") int bookId,
                            @RequestParam("holder_id") int holderId) {
        bookService.setHolder(bookId, holderId);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/removeHolder")
    public String removeHolder(@PathVariable int id) {
        bookService.removeHolder(id);

        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "books/search";
    }
    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("search_text") String text) {
        model.addAttribute("books", bookService.searchByTitle(text));
        return "books/search";
    }
}
