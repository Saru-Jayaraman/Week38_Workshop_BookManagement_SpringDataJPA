package se.lexicon.week38_workshop_appuserdetails.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.week38_workshop_appuserdetails.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DetailsRepositoryTest {
    @Autowired
    DetailsRepository detailsRepository;

    @Test
    @Transactional
    public void testSaveAndFindById() {
        Details details = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2024, 9, 19));
        Details savedDetails = detailsRepository.save(details);
        Assertions.assertNotNull(savedDetails);
        Assertions.assertNotNull(savedDetails.getId());

        Optional<Details> detailsOptional = detailsRepository.findById(savedDetails.getId());
        Assertions.assertTrue(detailsOptional.isPresent());
    }

    @Test
    @Transactional
    public void testGetDetailsByEmail() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2024, 9, 19));
        Details savedDetails1 = detailsRepository.save(details1);
        Assertions.assertNotNull(savedDetails1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        detailsRepository.save(details2);

        Optional<Details> detailsOptional = detailsRepository.getDetailsByEmail(savedDetails1.getEmail());
        Assertions.assertTrue(detailsOptional.isPresent());
        Assertions.assertNotNull(detailsOptional);
    }

    @Test
    @Transactional
    public void testGetDetailsByNameEqualsIgnoreCase() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2024, 9, 19));
        Details savedDetails1 = detailsRepository.save(details1);
        Assertions.assertNotNull(savedDetails1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        detailsRepository.save(details2);

        Optional<Details> detailsOptional = detailsRepository.getDetailsByNameEqualsIgnoreCase("mark justin");
        Assertions.assertTrue(detailsOptional.isPresent());
        Assertions.assertNotNull(detailsOptional);
    }

    @Test
    @Transactional
    public void testGetDetailsByNameContains() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2024, 9, 19));
        Details savedDetails1 = detailsRepository.save(details1);
        Assertions.assertNotNull(savedDetails1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        detailsRepository.save(details2);

        List<Details> detailsList = detailsRepository.getDetailsByNameContains("mil");
        Assertions.assertNotNull(detailsList);
        Assertions.assertEquals(detailsList.get(0).toString(), savedDetails1.toString());
    }

    @Test
    @Transactional
    public void testGetAllByBirthDateBetween() {
        Details details1 = new Details("emilyjohnson@test.se", "Emily Johnson", LocalDate.of(2024, 9, 19));
        Details savedDetails1 = detailsRepository.save(details1);
        Assertions.assertNotNull(savedDetails1);

        Details details2 = new Details("markjustin@test.se", "Mark Justin", LocalDate.of(2001, 2, 2));
        Details savedDetails2 = detailsRepository.save(details2);

        List<Details> detailsList = detailsRepository.getAllByBirthDateBetween(LocalDate.of(2000,1,1),
                LocalDate.of(2001,12,31));
        Assertions.assertNotNull(detailsList);
        Assertions.assertEquals(detailsList.get(0).toString(), savedDetails2.toString());
    }
}
