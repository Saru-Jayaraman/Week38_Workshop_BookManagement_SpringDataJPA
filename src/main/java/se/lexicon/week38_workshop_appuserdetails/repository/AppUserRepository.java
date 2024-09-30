package se.lexicon.week38_workshop_appuserdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.week38_workshop_appuserdetails.entity.AppUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
//    Find a user by Username.
    Optional<AppUser> getAppUserByUserName(String userName);

//    Find users by registration date between two specific dates.
    List<AppUser> getAllByRegDateBetween(LocalDate regDate, LocalDate regDate2);

//    Find users by details id.
    Optional<AppUser> getAppUserByDetails_Id(Integer details_id);

//    Find user by email ignore case.
    Optional<AppUser> findByDetailsEmailIgnoreCase(String email);

//    Find user by  username and password.
    Optional<AppUser> getAppUserByUserNameAndPassword(String userName, String password);
}