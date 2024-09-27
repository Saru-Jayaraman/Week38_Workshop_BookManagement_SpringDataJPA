package se.lexicon.week38_workshop_appuserdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.*;
import se.lexicon.week38_workshop_appuserdetails.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private AppUserRepository appUserRepository;

    private DetailsRepository detailsRepository;

    private BookRepository bookRepository;

    private BookLoanRepository bookLoanRepository;

    private AuthorRepository authorRepository;

    @Autowired
    public MyCommandLineRunner(AppUserRepository appUserRepository, DetailsRepository detailsRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository, AuthorRepository authorRepository) {
        this.appUserRepository = appUserRepository;
        this.detailsRepository = detailsRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        AppUser appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        detailsRepository.save(details1);
//        appUserRepository.save(appUser1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        AppUser appUser2 = new AppUser(details2.getName(), "mark123", LocalDate.of(2024,2,2), details2);
        detailsRepository.save(details2);
//        appUserRepository.save(appUser2);

        Details details3 = new Details("emilyjustin@test.se", "Emily Justin", LocalDate.of(2002, 8, 8));
        AppUser appUser3 = new AppUser(details3.getName(), "emily123", LocalDate.of(2024,8,8), details3);
        detailsRepository.save(details3);
//        appUserRepository.save(appUser3);

        Book book1 = new Book("bookisbn1", "Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1); // Assign a book to app user
        bookLoanRepository.save(bookLoan1);

        Book book2 = new Book("bookisbn2", "Book2", 30);
        BookLoan bookLoan2 = new BookLoan(book2, appUser2);
        bookLoanRepository.save(bookLoan2);

        Book book3 = new Book("bookisbn3", "Book3", 10);
        BookLoan bookLoan3 = new BookLoan(book3, appUser3);
        bookLoanRepository.save(bookLoan3);
        System.out.println();

        Book book4 = new Book("bookisbn4", "Book4", 10);
        bookRepository.save(book4);
        Book book5 = new Book("bookisbn5", "Book5", 10);
        bookRepository.save(book5);

        Author author1 = new Author("Author1", "Author1");
        Author author2 = new Author("Author2", "Author2");
        Author author3 = new Author("Author3", "Author3");
        Author author4 = new Author("Author4", "Author4");
        Author author5 = new Author("Author4", "Author4");

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);
        authorRepository.save(author5);

        author1.addBook(book1);
        book2.addAuthor(author2);
        book3.addAuthor(author3);
        book3.addAuthor(author4);
        author5.addBook(book4);
        author5.addBook(book5);

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);
        authorRepository.save(author5);

        System.out.println("----------------------------------------LOAN BOOK--------------------------------------");
        BookLoan bookLoan4 = new BookLoan(book1, appUser2); // prints: "Book is not available and Assigned to some other person... Please loan some other book.."

        System.out.println("----------------------------------------RETURN BOOK-------------------------------------");
        bookLoan1.returnBook(); // Returns the book
        bookLoanRepository.save(bookLoan1);
        System.out.println(bookLoan1);
        Optional<Book> optionalBook = bookRepository.findById(1);
        optionalBook.ifPresent(System.out::println);
        System.out.println();

        System.out.println("------------------------------------AUTHOR REPOSITORY-----------------------------------");
        System.out.println("------------------------------------FIND BY FIRST NAME----------------------------------");
        List<Author> findAuthorByFirstNameList = authorRepository.findByFirstName(author1.getFirstName());
        findAuthorByFirstNameList.forEach(System.out::println);
        System.out.println();

        System.out.println("------------------------------------FIND BY LAST NAME-----------------------------------");
        List<Author> findAuthorByLastNameList = authorRepository.findByLastName(author1.getLastName());
        findAuthorByLastNameList.forEach(System.out::println);
        System.out.println();

        System.out.println("--------------------------FIND BY FIRST NAME/LAST NAME CONTAINS-------------------------");
        String nameContains = "Author";
        List<Author> findAuthorByContains = authorRepository.findByFirstNameOrLastNameContaining(nameContains);
        findAuthorByContains.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------------FIND BY BOOK ID------------------------------------");
        List<Author> findAuthorByBookId = authorRepository.findByWrittenBooks_Id(book4.getId());
        findAuthorByBookId.forEach(System.out::println);

        List<Author> findAuthorByBookId1 = authorRepository.findByWrittenBooks_Id(book3.getId());
        findAuthorByBookId1.forEach(System.out::println);
        System.out.println();

        System.out.println("------------------------------------UPDATE BY BOOK ID-----------------------------------");
        String updateFirstName = "Auth1";
        String updateLastName = "Auth1";
        int updatedCount = authorRepository.updateByBookId(updateFirstName, updateLastName, book1.getId());
        System.out.println("Updated rows: " + updatedCount);
        authorRepository.save(author1);
        List<Author> updatedList = authorRepository.findByFirstName(updateFirstName);
        updatedList.forEach(System.out::println);

        System.out.println("-----------------------------------DELETE BY AUTHOR ID----------------------------------");
        authorRepository.deleteByAuthorId(author2.getId());
        List<Author> allAuthors = (List<Author>) authorRepository.findAll();
        allAuthors.forEach(System.out::println);
    }
}