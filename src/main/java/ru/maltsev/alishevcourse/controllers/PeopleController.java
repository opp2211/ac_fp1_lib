package ru.maltsev.alishevcourse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maltsev.alishevcourse.model.Person;
import ru.maltsev.alishevcourse.service.PersonService;
import ru.maltsev.alishevcourse.util.springvalidator.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }
    @GetMapping("/new")
    public String showAddForm(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String addNew(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        personService.addNew(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Person personWithBooks = personService.getByIdWithBooks(id);
        model.addAttribute("person", personWithBooks);
        model.addAttribute("books", personWithBooks.getBooks());
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("person", personService.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
