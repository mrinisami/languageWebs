package app.sami.languageWeb.language;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.factories.LanguageGradesFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.language.LanguageGradesRepository;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LanguageControllerTests extends IntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageGradesRepository languageGradesRepository;

    private User userTest1;
    private User userTest2;
    private User userTest3;
    private User userEvaluator;
    private User userEvaluator2;
    private LanguageGrades languageGradesTest1;
    private LanguageGrades languageGradesTest2;
    private Double grade1 = 50.0;
    private Double grade2 = 100.0;
    @BeforeEach
    void setup(){
        languageGradesRepository.deleteAll();
        userRepository.deleteAll();
        userTest1 = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        userEvaluator = userRepository.save(UserFactory.evaluatorGenerator());
        userEvaluator2 = userRepository.save(UserFactory.evaluatorGenerator());
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest1.getId(), userTest2.getId(), grade1, Language.ARABIC));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest1.getId(), userTest3.getId(), grade2, Language.ARABIC));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest1.getId(), userEvaluator.getId(), grade2, Language.ENGLISH));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest1.getId(), userEvaluator2.getId(), grade1, Language.ENGLISH));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest2.getId(), userTest1.getId(), grade1, Language.ROMANIAN));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest2.getId(), userTest2.getId(), grade1, Language.MACEDONIAN));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
                userTest2.getId(), userTest2.getId(), grade1, Language.FAROESE));
    }

    @Test
    void getUserGradeStats_ReturnsUserStatsAnd200() throws Exception {

        Language language = Language.ARABIC;
        UUID userId = userTest1.getId();
        String url = "/public/users/" + userId +  "/" + language + "/user-grade";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avgGrade").value(75));
    }

    @Test
    void givenWrongRouteReturns4xx()throws Exception{
        Language language = Language.ARABIC;
        UUID userId = UUID.randomUUID();
        String url = "/public/users/" + userId +  "/" + language + "/user-grades";
        mockMvc.perform(get(url))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getUserLanguages_ReturnsLanguageGradesListAnd200() throws Exception{
        UUID userId = userTest2.getId();
        String url = "/public/users/" + userId + "/languages";
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }


}
