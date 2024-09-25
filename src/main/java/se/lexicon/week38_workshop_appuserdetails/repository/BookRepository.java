package se.lexicon.week38_workshop_appuserdetails.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.week38_workshop_appuserdetails.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
//    Find book by isbn ignore case.
    Optional<Book> findByIsbnIgnoreCase(String isbn);

//    List<Book> findAllByIsbnIgnoreCase(String isbn);

//    Find book by title contains.
    List<Book> findByTitleContaining(String title);

//    Find books with maximum loan days less than a specified number.
    List<Book> findByMaxLoanDaysLessThan(int maxLoanDays);
}
