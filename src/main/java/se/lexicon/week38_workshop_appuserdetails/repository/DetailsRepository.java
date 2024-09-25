package se.lexicon.week38_workshop_appuserdetails.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.week38_workshop_appuserdetails.entity.Details;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetailsRepository extends CrudRepository<Details, Integer> {
//    Find a details by email.
    Optional<Details> getDetailsByEmail(String email);

//    Find details by name contains.
    List<Details> getDetailsByNameContains(String name);

//    Find details by name ignore-case.
    Optional<Details> getDetailsByNameEqualsIgnoreCase(String name);

//    Find details whose birthdate between 2 provided dates.
    List<Details> getAllByBirthDateBetween(LocalDate birthDate, LocalDate birthDate2);
}
