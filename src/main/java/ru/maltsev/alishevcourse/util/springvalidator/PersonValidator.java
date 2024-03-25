package ru.maltsev.alishevcourse.util.springvalidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maltsev.alishevcourse.model.Person;
import ru.maltsev.alishevcourse.service.PersonService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personService.getByFio(person.getFio()).isPresent())
            errors.rejectValue("fio", "", "Человек с указанным ФИО уже существует");
        if (person.getYearOfBirth() > LocalDate.now().getYear())
            errors.rejectValue("yearOfBirth", "", "Год рождения должен быть меньше текущего");
    }
}
