package ru.maltsev.alishevcourse.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {
    private static final int BOOK_EXPIRATION = 10 * 24*60*60*1000; // 10 days

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String title; //title better

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String author;

    @Min(value = 300, message = "Год должен быть больше, чем 300")
    @Max(value = 2024, message = "Год рождения должен быть меньше текущего") //Нужна кастомная валидация
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "holder_id")
    private Person holder;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    public boolean isExpired() {
        return Date.from(Instant.now()).getTime() - takenAt.getTime() > BOOK_EXPIRATION;
    }
}
