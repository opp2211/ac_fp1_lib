package ru.maltsev.alishevcourse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String name; //title better
    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String author;
    @Min(value = 300, message = "Год должен быть больше, чем 300")
    @Max(value = 2024, message = "Год рождения должен быть меньше текущего") //Нужна кастомная валидация
    private Integer year;
    private Integer holderId;
}
