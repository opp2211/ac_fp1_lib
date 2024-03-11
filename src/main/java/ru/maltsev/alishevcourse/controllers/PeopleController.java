package ru.maltsev.alishevcourse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maltsev.alishevcourse.dao.PersonDao;
import ru.maltsev.alishevcourse.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PersonDao personDao;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "people/index";
    }
    @GetMapping("/new")
    public String showAddForm(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String addNew(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDao.addNew(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        System.out.println(model.getAttribute("person"));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        System.out.println(person);
        if (bindingResult.hasErrors())
            return "people/edit";

        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personDao.delete(id);
        return "redirect:people";
    }
}
