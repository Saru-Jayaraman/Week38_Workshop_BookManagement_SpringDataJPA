package se.lexicon.week38_workshop_appuserdetails.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.AppUser;
import se.lexicon.week38_workshop_appuserdetails.entity.Book;
import se.lexicon.week38_workshop_appuserdetails.entity.BookLoan;
import se.lexicon.week38_workshop_appuserdetails.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookLoanRepositoryTest {
    @Autowired
    DetailsRepository detailsRepository;

    @Autowired
    BookLoanRepository bookLoanRepository;

    @Test
    @Transactional
    public void testFindByBorrower_Id() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan1);

        List<BookLoan> bookLoanList = bookLoanRepository.findByBorrower_Id(bookLoan1.getBorrower().getId());
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByBook_Id() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan1);

        List<BookLoan> bookLoanList = bookLoanRepository.findByBook_Id(bookLoan1.getBook().getId());
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByReturnedFalse() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan1);

        List<BookLoan> bookLoanList = bookLoanRepository.findByReturnedFalse();
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByOverDueBookLoans() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan1);

        bookLoan1.setLoanDate(LocalDate.of(2024, 9, 4));
        bookLoan1.setDueDate(LocalDate.of(2024, 9, 24));
        bookLoanRepository.save(bookLoan1);

        List<BookLoan> bookLoanList = bookLoanRepository.findByOverDueBookLoans();
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByLoanDateBetween() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        BookLoan savedBookLoan = bookLoanRepository.save(bookLoan1);

        LocalDate startDate = LocalDate.of(2024, 9, 24);
        LocalDate endDate = LocalDate.of(2024, 9, 26);
        List<BookLoan> bookLoanList = bookLoanRepository.findByLoanDateBetween(startDate, endDate);
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testUpdateByBookLoanId() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        bookLoanRepository.save(bookLoan1);

        bookLoanRepository.updateByBookLoanId(bookLoan1.getId());
        Optional<BookLoan> updatedBookLoan = bookLoanRepository.findById(bookLoan1.getId());
        Assertions.assertTrue(updatedBookLoan.isPresent());
        Assertions.assertNotNull(updatedBookLoan.get());
        Assertions.assertTrue(updatedBookLoan.get().isReturned());
    }
}
