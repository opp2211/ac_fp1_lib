package ru.maltsev.alishevcourse.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maltsev.alishevcourse.dao.PersonDao;
import ru.maltsev.alishevcourse.model.Person;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDao personDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDao.getByFio(person.getFio()).isPresent())
            errors.rejectValue("fio", "", "Человек с указанным ФИО уже существует");
        if (person.getYearOfBirth() > LocalDate.now().getYear())
            errors.rejectValue("yearOfBirth", "", "Год рождения должен быть меньше текущего");
    }
}
