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
    private final AppUserRepository appUserRepository;

    private final DetailsRepository detailsRepository;

    private final BookRepository bookRepository;

    private final BookLoanRepository bookLoanRepository;

    private final AuthorRepository authorRepository;

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

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        AppUser appUser2 = new AppUser(details2.getName(), "mark123", LocalDate.of(2024,2,2), details2);

        Details details3 = new Details("emilyjustin@test.se", "Emily Justin", LocalDate.of(2002, 8, 8));
        AppUser appUser3 = new AppUser(details3.getName(), "emily123", LocalDate.of(2024,8,8), details3);

        Book book1 = new Book("Book1", 20);
        BookLoan bookLoan1 = new BookLoan(book1, appUser1); // Assign a book to app user
        bookLoanRepository.save(bookLoan1);

        Book book2 = new Book("Book2", 30);
        BookLoan bookLoan2 = new BookLoan(book2, appUser2);
        bookLoanRepository.save(bookLoan2);

        Book book3 = new Book("Book3", 10);
        BookLoan bookLoan3 = new BookLoan(book3, appUser3);
        bookLoanRepository.save(bookLoan3);
        System.out.println();

        Book book4 = new Book("Book4", 10);
        bookRepository.save(book4);

        Book book5 = new Book("Book5", 10);
        bookRepository.save(book5);

        Author author1 = new Author("Author1", "Author1");
        Author author2 = new Author("Author2", "Author2");
        Author author3 = new Author("Author3", "Author3");
        Author author4 = new Author("Author4", "Author4");
        Author author5 = new Author("Author4", "Author4");

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

                System.out.println("-----------------------------------APP USER REPOSITORY----------------------------------");
        System.out.println("------------------------------------FIND BY USERNAME------------------------------------");
        Optional<AppUser> findByUserName = appUserRepository.getAppUserByUserName(appUser2.getUserName());
        findByUserName.ifPresent(System.out::println);
        System.out.println();

        System.out.println("--------------------------------FIND BETWEEN 2 REG DATES--------------------------------");
        LocalDate startRegDate = LocalDate.of(2024,1,1);
        LocalDate endRegDate = LocalDate.of(2024,12,31);
        List<AppUser> findBetweenTwoRegDatesList = appUserRepository.getAllByRegDateBetween(startRegDate, endRegDate);
        findBetweenTwoRegDatesList.forEach(System.out::println);
        System.out.println();

        System.out.println("-----------------------------------FIND BY DETAILS ID-----------------------------------");
        Optional<AppUser> findByDetailsId = appUserRepository.getAppUserByDetails_Id(appUser2.getDetails().getId());
        findByDetailsId.ifPresent(System.out::println);
        System.out.println();

        System.out.println("-------------------------------FIND BY DETAILS EMAIL ID---------------------------------");
        Optional<AppUser> findByDetailsEmail = appUserRepository.findByDetailsEmailIgnoreCase(details1.getEmail());
        findByDetailsEmail.ifPresent(System.out::println);
        System.out.println();

        System.out.println("------------------------------FIND BY USERNAME AND PASSWORD-----------------------------");
        Optional<AppUser> findByUserPass = appUserRepository.getAppUserByUserNameAndPassword(appUser2.getUserName(), appUser2.getPassword());
        findByUserPass.ifPresent(System.out::println);
        System.out.println();

        System.out.println("-----------------------------------DETAILS REPOSITORY-----------------------------------");
        System.out.println("-------------------------------------FIND BY EMAIL--------------------------------------");
        Optional<Details> findByEmail = detailsRepository.getDetailsByEmail(details2.getEmail());
        findByEmail.ifPresent(System.out::println);
        System.out.println();

        System.out.println("---------------------------------FIND BY NAME CONTAINS----------------------------------");
        String findByNameContains = "mil";
        List<Details> findByNameContainsList = detailsRepository.getDetailsByNameContains(findByNameContains);
        System.out.println(findByNameContainsList);
        System.out.println();

        System.out.println("----------------------------FIND BY NAME EQUALS IGNORE CASE-----------------------------");
        String findByNameString = "emily justin";
        Optional<Details> findByNameEqualsIgnoreCase = detailsRepository.getDetailsByNameEqualsIgnoreCase(findByNameString);
        findByNameEqualsIgnoreCase.ifPresent(System.out::println);
        System.out.println();

        System.out.println("-------------------------------FIND BETWEEN 2 BIRTH DATES-------------------------------");
        LocalDate startBirthDate = LocalDate.of(2000,1,1);
        LocalDate endBirthDate = LocalDate.of(2001,12,31);
        List<Details> findBetweenTwoBirthDatesList = detailsRepository.getAllByBirthDateBetween(startBirthDate, endBirthDate);
        findBetweenTwoBirthDatesList.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------------BOOK REPOSITORY------------------------------------");
        System.out.println("--------------------------- -----FIND BY ISBN IGNORE CASE-------------------------------");
        Optional<Book> findBookByIsbnOptional = bookRepository.findByIsbnIgnoreCase(book1.getIsbn());
        findBookByIsbnOptional.ifPresent(System.out::println);
        System.out.println();

        System.out.println("--------------------------- -----FIND BY TITLE CONTAINING-------------------------------");
        String findBookByTitleContains = "ook";
        List<Book> findBookByTitleContainsList = bookRepository.findByTitleContaining(findBookByTitleContains);
        findBookByTitleContainsList.forEach(System.out::println);
        System.out.println();

        System.out.println("---------------------------FIND BY MAX DAYS LESS THAN A NUMBER--------------------------");
        int number = 25;
        List<Book> findBookByMaxDays = bookRepository.findByMaxLoanDaysLessThan(number);
        findBookByMaxDays.forEach(System.out::println);
        System.out.println();

        System.out.println("----------------------------------BOOK LOAN REPOSITORY----------------------------------");
        System.out.println("-----------------------------------FIND BY BORROWER ID----------------------------------");
        List<BookLoan> findBookLoanByBorrowerIdList = bookLoanRepository.findByBorrower_Id(bookLoan2.getBorrower().getId());
        findBookLoanByBorrowerIdList.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------------FIND BY BOOK ID------------------------------------");
        List<BookLoan> findBookLoanByBookIdList = bookLoanRepository.findByBook_Id(bookLoan1.getBook().getId());
        findBookLoanByBookIdList.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------FIND BOOK LOANS NOT RETURNED-----------------------------");
        List<BookLoan> findBookLoanNotReturned = bookLoanRepository.findByReturnedFalse();
        findBookLoanNotReturned.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------------FIND BY OVERDUE------------------------------------");
        bookLoan1.setLoanDate(LocalDate.of(2024, 9, 4));
        bookLoan1.setDueDate(LocalDate.of(2024, 9, 24));
        bookLoanRepository.save(bookLoan1);
        List<BookLoan> findBookLoanByOverDue = bookLoanRepository.findByOverDueBookLoans();
        findBookLoanByOverDue.forEach(System.out::println);
        System.out.println();

        System.out.println("------------------------------FIND BOOK LOAN BETWEEN 2 DATES----------------------------");
        LocalDate startDate = LocalDate.of(2024, 9, 24);
        LocalDate endDate = LocalDate.of(2024, 9, 26);
        List<BookLoan> findBookLoanBetweenTwoDate = bookLoanRepository.findByLoanDateBetween(startDate, endDate);
        findBookLoanBetweenTwoDate.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------UPDATE BOOK LOAN BY LOAN ID------------------------------");
        bookLoanRepository.updateByBookLoanId(bookLoan2.getId());
        Optional<BookLoan> updatedBookLoan = bookLoanRepository.findById(bookLoan2.getId());
        System.out.println(updatedBookLoan);
        System.out.println();


        System.out.println("----------------------------------------LOAN BOOK--------------------------------------");
        BookLoan bookLoan4 = new BookLoan(book1, appUser2); // prints: "Book is not available and Assigned to some other person... Please loan some other book."

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

        System.out.println("-----------------------------------UPDATE BY AUTHOR ID----------------------------------");
        String updateFirstName = "Auth1";
        String updateLastName = "Auth1";
        int updatedCount = authorRepository.updateByAuthorId(updateFirstName, updateLastName, author1.getId());
        System.out.println("Updated rows: " + updatedCount);
        Optional<Author> foundAuthor = authorRepository.findById(author1.getId());
        foundAuthor.ifPresent(System.out::println);

        System.out.println("-----------------------------------DELETE BY AUTHOR ID----------------------------------");
        authorRepository.deleteByAuthorId(author2.getId());
        List<Author> allAuthors = (List<Author>) authorRepository.findAll();
        allAuthors.forEach(System.out::println);
    }
}