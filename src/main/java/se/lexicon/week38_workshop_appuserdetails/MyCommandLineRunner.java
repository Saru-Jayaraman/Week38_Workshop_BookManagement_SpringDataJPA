package se.lexicon.week38_workshop_appuserdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.AppUser;
import se.lexicon.week38_workshop_appuserdetails.entity.Book;
import se.lexicon.week38_workshop_appuserdetails.entity.BookLoan;
import se.lexicon.week38_workshop_appuserdetails.entity.Details;
import se.lexicon.week38_workshop_appuserdetails.repository.AppUserRepository;
import se.lexicon.week38_workshop_appuserdetails.repository.BookLoanRepository;
import se.lexicon.week38_workshop_appuserdetails.repository.BookRepository;
import se.lexicon.week38_workshop_appuserdetails.repository.DetailsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private AppUserRepository appUserRepository;

    private DetailsRepository detailsRepository;

    private BookRepository bookRepository;

    private BookLoanRepository bookLoanRepository;

    @Autowired
    public MyCommandLineRunner(AppUserRepository appUserRepository, DetailsRepository detailsRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository, LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.appUserRepository = appUserRepository;
        this.detailsRepository = detailsRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
    }

    @Override
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
        BookLoan bookLoan1 = new BookLoan(book1, appUser1);
        bookLoanRepository.save(bookLoan1);

        Book book2 = new Book("bookisbn2", "Book2", 30);
        BookLoan bookLoan2 = new BookLoan(book2, appUser2);
        bookLoanRepository.save(bookLoan2);

        Book book3 = new Book("bookisbn3", "Book3", 10);
        BookLoan bookLoan3 = new BookLoan(book3, appUser3);
        bookLoanRepository.save(bookLoan3);
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

        System.out.println("------------------------------FIND BOOK LOAN BETWEEN 2 DATES---------------------------");
        LocalDate startDate = LocalDate.of(2024, 9, 24);
        LocalDate endDate = LocalDate.of(2024, 9, 26);
        List<BookLoan> findBookLoanBetweenTwoDate = bookLoanRepository.findByLoanDateBetween(startDate, endDate);
        findBookLoanBetweenTwoDate.forEach(System.out::println);
        System.out.println();

        System.out.println("-------------------------------UPDATE BOOK LOAN BY LOAN ID-----------------------------");
        bookLoanRepository.updateByBookLoanId(bookLoan2.getId());
        System.out.println(bookLoan2);
        System.out.println();

        System.out.println("-------------------------------RETURN BOOK BY APP USER-----------------------------");
        bookLoan1.returnBook();
        bookLoanRepository.save(bookLoan1);
        System.out.println(bookLoan1);
    }
}