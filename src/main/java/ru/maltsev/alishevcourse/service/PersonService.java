package ru.maltsev.alishevcourse.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maltsev.alishevcourse.model.Person;
import ru.maltsev.alishevcourse.repo.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void addNew(Person person) {
        personRepository.save(person);
    }

    public Person getById(int id) {
        return personRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Person ID = %d not found!", id)));
    }

    public Person getByIdWithBooks(int id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Person ID = %d not found!", id)));
        Hibernate.initialize(person.getBooks());
        return person;
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> getByFio(String fio) {
        return personRepository.findByFio(fio);
    }
}
