package app.sami.languageWeb.language;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.LanguageGradesFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LanguageControllerTests extends IntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageGradesRepository languageGradesRepository;

    @BeforeEach
    void setup(){
        languageGradesRepository.deleteAll();
    }

    @Test
    void getUserGradeStats_ReturnsUserStatsAnd200() throws Exception {
        User user1 = userRepository.save(UserFactory.userGenerator());
        User user2 = userRepository.save(UserFactory.userGenerator());

        double grade1 = 5;
        double grade2 = 10;

        LanguageGrades languageGrades1 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), user2.getId(), grade1, Language.ENGLISH);
        LanguageGrades languageGrades2 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), user2.getId(), grade2, Language.ENGLISH);

        languageGradesRepository.save(languageGrades1);
        languageGradesRepository.save(languageGrades2);
        Language language = Language.ENGLISH;
        UUID userId = user1.getId();
        String url = "/public/users/" + userId +  "/" + language + "/user-grade";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }
}
