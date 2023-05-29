package app.sami.languageWeb.user;

import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTests extends IntegrationTests {
    @Autowired
    UserRepository userRepository;
    private User userTest1;
    private User userTest2;
    private User userTest3;

    @BeforeEach
    void setup(){
        userTest1 = userRepository.save(UserFactory.userGenerator()
                .withFirstName("Cambucca"));
        userTest2 = userRepository.save(UserFactory.userGenerator()
                .withLastName("Cocial"));
        userTest3 = userRepository.save(UserFactory.userGenerator()
                .withFirstName("Benjamin"));
    }

    @Test
    void matchingListOfUsers_ReturnTrue(){
        List<User> result = userRepository.findByFirstNameLastNameLike("c", PageRequest.of(0, 2));
        List<User> expected = new ArrayList<>();
        expected.add(userTest1);
        expected.add(userTest2);

        assertThatList(result).hasSameElementsAs(expected);
    }
}
