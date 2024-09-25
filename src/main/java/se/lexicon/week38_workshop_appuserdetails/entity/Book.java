package se.lexicon.week38_workshop_appuserdetails.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(unique = true, nullable = false, updatable = false)
    private String isbn;

    @Setter
    @Column(nullable = false, updatable = false, length = 100)
    private String title;

    @Setter
    @Column(updatable = false, nullable = false)
    private int maxLoanDays;

    public Book(String isbn, String title, int maxLoanDays) {
        this.isbn = isbn;
        this.title = title;
        this.maxLoanDays = maxLoanDays;
    }
}
