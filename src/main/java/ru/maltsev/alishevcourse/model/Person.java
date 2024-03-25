package ru.maltsev.alishevcourse.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String fio;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "holder")
    private List<Book> books;
}
