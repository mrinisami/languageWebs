package app.sami.languageWeb.language;

import app.sami.languageWeb.auth.services.JwtService;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LanguageControllerTests extends IntegrationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageGradesRepository languageGradesRepository;
    @Autowired
    private JwtService jwtService;
    private User userTest1;
    private User userTest2;
    private User userTest3;
    private User userEvaluator;
    private User userEvaluator2;
    private LanguageGrades languageGradesTest1;
    private LanguageGrades languageGradesTest2;
    private final Double grade1 = 50.0;
    private final Double grade2 = 100.0;
    @BeforeEach
    void setup(){
        userTest1 = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        userEvaluator = userRepository.save(UserFactory.evaluatorGenerator());
        userEvaluator2 = userRepository.save(UserFactory.evaluatorGenerator());
        languageGradesTest1 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade1)
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest2.getId())
                .withRefLanguage(Language.MACEDONIAN));
    }

    @Test
    void getUserLanguages_ReturnsLanguageGradesListAnd200() throws Exception{
        UUID userId = userTest2.getId();
        String url = String.format("/public/users/%s/languages", userId);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    void createLanguageGrade_ReturnsLanguageGradeAnd200() throws Exception {
        UUID userId = userTest2.getId();
        String token = authUser(userTest1);
        String url = String.format("/users/%s/languages/%s/grades", userId, Language.MACEDONIAN);
        LanguageGradeRequest languageGradeRequest = new LanguageGradeRequest(grade2);
        mockMvc.perform(post(url, languageGradeRequest, token))
                .andExpect(status().isOk());

    }

    @Test
    void givenNoPriorSelfAssessment_Returns404() throws Exception {
        UUID userId = userTest2.getId();
        UUID emitterId = userTest1.getId();
        String token = authUser(userTest1);
        String url = String.format("/users/%s/languages/%s/grades", userId, Language.FULA);

        LanguageGradeRequest languageGradeRequest = new LanguageGradeRequest(grade1);

        mockMvc.perform(post(url, languageGradeRequest, token))
                .andExpect(status().isNotFound());
    }

    @Test
    void matchLanguageGrades_Returns200AndTrue() throws Exception {
        UUID userId = userTest2.getId();

        String url = String.format("/public/users/%s/languageGrades/summary", userId);

        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }
    @Test
    void givenWrongUserLanguageGrades_Returns404() throws Exception {
        String url = "/public/users/" + UUID.randomUUID() + "/languages/grades";

        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenEmitterIsDifferentFromUserAuth_Returns403() throws Exception {
        UUID userId = userTest2.getId();
        String url = String.format("/users/%s/languageGrade/%s", userId, languageGradesTest1.getId());
        String token = authUser(userTest1);

        mockMvc.perform(put(url, new LanguageGradeRequest(languageGradesTest1.getGrade()), token))
                .andExpect(status().isForbidden());
    }

    @Test
    void validRequestEditLanguage_Returns200() throws Exception {
        UUID userId = userTest2.getId();
        String url = String.format("/users/%s/languageGrade/%s", userId, languageGradesTest1.getId());
        String token = authUser(userTest2);

        mockMvc.perform(put(url, new LanguageGradeRequest(57.0), token))
                .andExpect(status().isOk());
    }
}
