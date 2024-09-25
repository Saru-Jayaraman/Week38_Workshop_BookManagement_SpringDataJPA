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

    @Setter
    @Column(nullable = false)
    private LocalDate loanDate;

    @Setter
    @Column(nullable = false)
    private LocalDate dueDate;

    @Setter
    @Column(nullable = false)
    private boolean returned;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private AppUser borrower;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;

    public BookLoan(Book book, AppUser appUser) {
        //consider the book is always available to assign it to the app user
        this.borrower = appUser;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(book.getMaxLoanDays());
        this.returned = false;
    }

    public void returnBook() {
        this.borrower = null;
        this.book = null;
//        if(this.loanDate.plusDays(book.getMaxLoanDays()).isAfter(this.dueDate)) {
//            //Pay fine
//        }
        this.returned = true;
    }
}
