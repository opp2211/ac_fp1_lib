package ru.maltsev.alishevcourse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String fio;
    @Min(1900)
    @Max(2024) // todo: нормальная валидация
    private Integer yearOfBirth;
}
