package ru.maltsev.alishevcourse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maltsev.alishevcourse.dao.PersonDao;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PersonDao personDao;

    @GetMapping
    private String showAll(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "people/index";
    }
}
