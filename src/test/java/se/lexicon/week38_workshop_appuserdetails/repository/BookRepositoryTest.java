package se.lexicon.week38_workshop_appuserdetails.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.Book;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    Book savedBook;

    @BeforeEach
    public void setUp() {
        Book book1 = new Book("Book1", 20);
        savedBook = bookRepository.save(book1);
    }

    @Test
    @Transactional
    public void testFindByIsbnIgnoreCase() {
        Optional<Book> bookOptional = bookRepository.findByIsbnIgnoreCase(savedBook.getIsbn());
        Assertions.assertTrue(bookOptional.isPresent());
        Assertions.assertNotNull(bookOptional.get());
    }

    @Test
    @Transactional
    public void testFindByTitleContaining() {
        List<Book> bookList = bookRepository.findByTitleContaining("Book");
        Assertions.assertNotNull(bookList);
        Assertions.assertEquals(bookList.get(0).toString(), savedBook.toString());
    }

    @Test
    @Transactional
    public void testFindByMaxLoanDaysLessThan() {
        List<Book> bookList = bookRepository.findByMaxLoanDaysLessThan(30);
        Assertions.assertNotNull(bookList);
        Assertions.assertEquals(bookList.get(0).toString(), savedBook.toString());
    }
}
