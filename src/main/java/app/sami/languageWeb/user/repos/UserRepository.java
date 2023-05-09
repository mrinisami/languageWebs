<<<<<<<< HEAD:src/main/java/app/sami/languageWeb/user/repos/UserRepository.java
package app.sami.languageWeb.user.repos;
========
package app.sami.languageWeb.user;
>>>>>>>> d7e460a (added languages tables and data):src/main/java/app/sami/languageWeb/user/UserRepository.java

import app.sami.languageWeb.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
