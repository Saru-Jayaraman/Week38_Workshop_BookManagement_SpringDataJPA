package se.lexicon.week38_workshop_appuserdetails.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    @Setter private String firstName;

    @Column(nullable = false, length = 50)
    @Setter private String lastName;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "authors_books",
        joinColumns = @JoinColumn(name = "author_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Setter private Set<Book> writtenBooks = new HashSet<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addBook(Book book) {
        writtenBooks.add(book);
        Set<Author> authorsSet = book.getAuthors();
        authorsSet.add(this);
        book.setAuthors(authorsSet);
    }

    public void removeBook(Book book) {
        writtenBooks.remove(book);
        Set<Author> authorSet = book.getAuthors();
        authorSet.remove(this);
        book.setAuthors(authorSet);
    }
}
