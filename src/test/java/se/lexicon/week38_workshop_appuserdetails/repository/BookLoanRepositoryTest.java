package se.lexicon.week38_workshop_appuserdetails.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    BookLoanRepository bookLoanRepository;

    BookLoan bookLoan1, savedBookLoan;

    @BeforeEach
    public void setUp() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);

        Book book1 = new Book("Book1", 20);
        bookLoan1 = new BookLoan(book1, appUser1);
        savedBookLoan = bookLoanRepository.save(bookLoan1);
    }

    @Test
    @Transactional
    public void testFindByBorrower_Id() {
        List<BookLoan> bookLoanList = bookLoanRepository.findByBorrower_Id(bookLoan1.getBorrower().getId());
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByBook_Id() {
        List<BookLoan> bookLoanList = bookLoanRepository.findByBook_Id(bookLoan1.getBook().getId());
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByReturnedFalse() {
        List<BookLoan> bookLoanList = bookLoanRepository.findByReturnedFalse();
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByOverDueBookLoans() {
        bookLoan1.setLoanDate(LocalDate.now().minusDays(20));
        bookLoan1.setDueDate(LocalDate.now().minusDays(18));
        bookLoanRepository.save(bookLoan1);

        List<BookLoan> bookLoanList = bookLoanRepository.findByOverDueBookLoans();
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByLoanDateBetween() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now().plusDays(2);
        List<BookLoan> bookLoanList = bookLoanRepository.findByLoanDateBetween(startDate, endDate);
        Assertions.assertNotNull(bookLoanList);
        Assertions.assertEquals(savedBookLoan.toString(), bookLoanList.get(0).toString());
    }

    @Test
    @Transactional
    public void testUpdateByBookLoanId() {
        bookLoanRepository.updateByBookLoanId(bookLoan1.getId());
        Optional<BookLoan> updatedBookLoan = bookLoanRepository.findById(bookLoan1.getId());
        Assertions.assertTrue(updatedBookLoan.isPresent());
        Assertions.assertNotNull(updatedBookLoan.get());
        Assertions.assertTrue(updatedBookLoan.get().isReturned());
    }
}
