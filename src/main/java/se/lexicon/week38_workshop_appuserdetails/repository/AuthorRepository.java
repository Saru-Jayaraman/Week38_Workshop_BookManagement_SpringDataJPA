package se.lexicon.week38_workshop_appuserdetails.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
//    Find authors by their first name.
    List<Author> findByFirstName(String firstName);

//    Find authors by their last name
    List<Author> findByLastName(String lastName);

//    Find authors by their first name or last name containing a keyword.
    @Query("select a from Author a where a.firstName like concat('%', :name, '%') or a.lastName like concat('%', :name, '%') ")
    List<Author> findByFirstNameOrLastNameContaining(String name);

//    Find authors by a book's ID.
    List<Author> findByWrittenBooks_Id(Integer bookId);

//    Update an author's name by their ID.
    @Transactional
    @Modifying
    @Query(value = "update author set first_name = :firstName, last_Name = :lastName where id in (select author_id from authors_books where book_id = :bookId)", nativeQuery = true)
    int updateByBookId(String firstName, String lastName, Integer bookId);

//    Delete an author by their ID.
    @Transactional
    @Modifying
    @Query(value = "delete from Author where id = :deleteId")
    void deleteByAuthorId(Integer deleteId);
}
