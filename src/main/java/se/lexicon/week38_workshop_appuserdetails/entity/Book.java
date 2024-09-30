package se.lexicon.week38_workshop_appuserdetails.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "authors")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false)
    @Setter private String isbn;

    @Column(nullable = false, updatable = false, length = 100)
    @Setter private String title;

    @Column(nullable = false)
    @Setter private int maxLoanDays;

    @Column(nullable = false)
    @Setter private boolean available;

    @ManyToMany(mappedBy = "writtenBooks")
    @Setter private Set<Author> authors = new HashSet<>();

    public Book(String title, int maxLoanDays) {
        this.isbn = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.maxLoanDays = maxLoanDays;
        this.available = true;
    }

    public void addAuthor(Author author) {
        authors.add(author);
        Set<Book> bookSet = author.getWrittenBooks();
        bookSet.add(this);
        author.setWrittenBooks(bookSet);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        Set<Book> bookSet = author.getWrittenBooks();
        bookSet.remove(this);
        author.setWrittenBooks(bookSet);
    }
}
