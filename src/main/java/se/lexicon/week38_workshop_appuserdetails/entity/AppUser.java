package se.lexicon.week38_workshop_appuserdetails.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password", "bookLoans"})
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    @Setter private String userName;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false)
    @Setter private LocalDate regDate;

    @OneToOne
    @JoinColumn
    @Setter private Details details;

    @OneToMany(mappedBy = "borrower")
    @Setter private Set<BookLoan> bookLoans = new HashSet<>();

    public AppUser(String userName, String password, Details details) {
        this.userName = userName;
        this.password = password;
        this.details = details;
        this.regDate = LocalDate.now();
    }

    public AppUser(String userName, String password, LocalDate regDate, Details details) {
        this.userName = userName;
        this.password = password;
        this.regDate = regDate;
        this.details = details;
    }

    public void addBookLoan(BookLoan bookLoan) {
        bookLoans.add(bookLoan);
        bookLoan.setBorrower(this);
    }

    public void removeBookLoan(BookLoan bookLoan) {
        bookLoans.remove(bookLoan);
        bookLoan.setBorrower(null);
    }
}
