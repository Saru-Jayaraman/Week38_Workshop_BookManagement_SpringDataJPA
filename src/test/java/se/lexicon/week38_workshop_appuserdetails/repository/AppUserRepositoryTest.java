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
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    AppUser appUser1, savedUser;
    Details details1;

    @BeforeEach
    public void setUp() {
        details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2000, 9, 9));
        appUser1 = new AppUser(details1.getName(), "emily123", LocalDate.of(2024,9,9), details1);
        savedUser = appUserRepository.save(appUser1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        AppUser appUser2 = new AppUser(details2.getName(), "mark123", LocalDate.of(2024,2,2), details2);
        appUserRepository.save(appUser2);
    }

    @Test
    @Transactional
    public void testSaveAndFindById() {
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());

        Optional<AppUser> appUserOptional = appUserRepository.findById(savedUser.getId());
        Assertions.assertTrue(appUserOptional.isPresent());
    }

    @Test
    @Transactional
    public void testGetAppUserByUserName() {
        Optional<AppUser> foundUser = appUserRepository.getAppUserByUserName(savedUser.getUserName());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertNotNull(foundUser);
    }

    @Test
    @Transactional
    public void testGetAppUserByDetailsId() {
        Optional<AppUser> foundUser = appUserRepository.getAppUserByDetails_Id(details1.getId());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertNotNull(foundUser);
    }

    @Test
    @Transactional
    public void testGetAllByRegDateBetween() {
        List<AppUser> foundUser = appUserRepository.getAllByRegDateBetween(LocalDate.of(2024,1,1),
                LocalDate.of(2024,12,31));
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(foundUser.get(0).toString(), savedUser.toString());
    }

    @Test
    @Transactional
    public void testFindByDetailsEmailIgnoreCase() {
        Optional<AppUser> foundUser = appUserRepository.findByDetailsEmailIgnoreCase(details1.getEmail());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertNotNull(foundUser);
    }

    @Test
    @Transactional
    public void testGetAppUserByUserNameAndPassword() {
        Optional<AppUser> foundUser = appUserRepository.getAppUserByUserNameAndPassword(appUser1.getUserName(), appUser1.getPassword());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertNotNull(foundUser);
    }
}
