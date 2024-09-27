package se.lexicon.week38_workshop_appuserdetails.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Setter private LocalDate loanDate;

    @Column(nullable = false)
    @Setter private LocalDate dueDate;

    @Column(nullable = false)
    @Setter private boolean returned;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @Setter private AppUser borrower;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @Setter private Book book;

    public BookLoan(Book book, AppUser appUser) {
        if(book.isAvailable()) {
            this.borrower = appUser;
            book.setAvailable(false);
            this.book = book;
            this.loanDate = LocalDate.now();
            this.dueDate = LocalDate.now().plusDays(book.getMaxLoanDays());
            this.returned = false;
        } else {
            System.out.println("Book is not available and Assigned to some other person... Please loan some other book...");
            new BookLoan();
        }
    }

    public void returnBook() {
        this.getBook().setAvailable(true);
        this.returned = true;
        this.book = null;
        this.borrower = null;
    }
}
