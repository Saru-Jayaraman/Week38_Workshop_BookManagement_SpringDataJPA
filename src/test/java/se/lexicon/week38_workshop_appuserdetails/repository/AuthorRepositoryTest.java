package se.lexicon.week38_workshop_appuserdetails.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookLoanRepository bookLoanRepository;

    Author author1, savedAuthor;
    Book book1;

    @BeforeEach
    public void setUp() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);

        book1 = new Book("Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        bookLoanRepository.save(bookLoan1);

        author1 = new Author("Author1", "Author1");
        author1.addBook(book1);
        savedAuthor = authorRepository.save(author1);
    }

    @Test
    @Transactional
    public void testFindByFirstName() {
        List<Author> authorList = authorRepository.findByFirstName(author1.getFirstName());
        Assertions.assertNotNull(authorList);
        Assertions.assertEquals(savedAuthor.toString(), authorList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByLastName() {
        List<Author> authorList = authorRepository.findByFirstName(author1.getLastName());
        Assertions.assertNotNull(authorList);
        Assertions.assertEquals(savedAuthor.toString(), authorList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByFirstNameOrLastNameContaining() {
        List<Author> authorList = authorRepository.findByFirstNameOrLastNameContaining(author1.getLastName());
        Assertions.assertNotNull(authorList);
        Assertions.assertEquals(savedAuthor.toString(), authorList.get(0).toString());
    }

    @Test
    @Transactional
    public void testFindByWrittenBooks_Id() {
        List<Author> authorList = authorRepository.findByWrittenBooks_Id(book1.getId());
        Assertions.assertNotNull(authorList);
        Assertions.assertEquals(savedAuthor.toString(), authorList.get(0).toString());
    }

    @Test
    @Transactional
    public void testUpdateByAuthorId() {
        String updateFirstName = "Auth1";
        String updateLastName = "Auth1";
        int updatedCount = authorRepository.updateByAuthorId(updateFirstName, updateLastName, author1.getId());
        Assertions.assertEquals(1, updatedCount);

        Optional<Author> updatedAuthor = authorRepository.findById(author1.getId());
        Assertions.assertTrue(updatedAuthor.isPresent());
        Assertions.assertNotNull(updatedAuthor.get());
        Assertions.assertEquals(updatedAuthor.get().getFirstName(), updateFirstName);
    }
}
