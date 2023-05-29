package app.sami.languageWeb.user.repos;



import app.sami.languageWeb.user.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query(value= "SELECT id, first_name, last_name, email, user_password, created_at, user_role " +
            "FROM users " +
            "WHERE LOWER(first_name) LIKE :name% " +
            "OR LOWER(last_name) LIKE :name%",
    nativeQuery = true)
    List<User> findByFirstNameLastNameLike(@Param("name") String name, PageRequest of);
}
