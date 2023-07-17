package app.sami.languageWeb.language;

import app.sami.languageWeb.auth.services.JwtService;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.IntegrationTests;
import app.sami.languageWeb.testUtils.Randomize;
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
        userRepository.deleteAll();
        userTest1 = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        userEvaluator = userRepository.save(UserFactory.evaluatorGenerator());
        userEvaluator2 = userRepository.save(UserFactory.evaluatorGenerator());
        languageGradesTest1 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade1)
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                        .withTranslatedLanguage(Language.FULA));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                        .withTranslatedLanguage(Language.FULA));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest2.getId())
                .withRefLanguage(Language.MACEDONIAN)
                .withTranslatedLanguage(Language.AKAN));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest1.getId())
                .withUserId(userTest2.getId())
                .withRefLanguage(Language.ENGLISH)
                .withTranslatedLanguage(Language.BAMBARA));
    }

    @Test
    void getUserLanguageByRefLanguageAndTranslatedLanguage_Returns200() throws Exception{
        String token = authUser(userTest1);
        String url = String.format("/users/%s/languageGrades", userTest2.getId());

        mockMvc.perform(get(url, token)
                        .param("refLanguage", Language.MACEDONIAN.toString())
                        .param("translatedLanguage", Language.AKAN.toString()))
                .andExpect(status().isOk());
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
        String url = String.format("/users/%s/languages/grade", userId);
        LanguageGradeRequest languageGradeRequest = LanguageGradeRequest.builder()
                .grade(grade2)
                .translatedLanguage(Language.AKAN)
                .refLanguage(Language.MACEDONIAN)
                .build();
        mockMvc.perform(post(url, languageGradeRequest, token))
                .andExpect(status().isOk());

    }

    @Test
    void givenNoPriorSelfAssessment_Returns404() throws Exception {
        UUID userId = userTest2.getId();
        UUID emitterId = userTest1.getId();
        String token = authUser(userTest1);
        String url = String.format("/users/%s/languages/grade", userId);

        LanguageGradeRequest languageGradeRequest = LanguageGradeRequest.builder()
                        .grade(grade2)
                                .translatedLanguage(Language.BAMBARA)
                                        .refLanguage(Language.SAMOAN)
                                                .build();

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
        String url = String.format("/public/users/%s/languages/grades", UUID.randomUUID());
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenEmitterIsDifferentFromUserAuth_Returns403() throws Exception {
        UUID userId = userTest2.getId();
        String url = String.format("/users/%s/languageGrade/%s", userId, languageGradesTest1.getId());
        String token = authUser(userTest1);
        LanguageGradeRequest languageGradeRequest = LanguageGradeRequest.builder()
                        .refLanguage(languageGradesTest1.getRefLanguage())
                                .translatedLanguage(languageGradesTest1.getTranslatedLanguage())
                                        .grade(57.0)
                                                .build();
        mockMvc.perform(put(url, languageGradeRequest, token))
                .andExpect(status().isForbidden());
    }

    @Test
    void validRequestEditLanguage_Returns200() throws Exception {
        UUID userId = userTest2.getId();
        String url = String.format("/users/%s/languageGrade/%s", userId, languageGradesTest1.getId());
        String token = authUser(userTest2);
        LanguageGradeRequest languageGradeRequest = LanguageGradeRequest.builder()
                .refLanguage(languageGradesTest1.getRefLanguage())
                .translatedLanguage(languageGradesTest1.getTranslatedLanguage())
                .grade(57.0)
                .build();
        mockMvc.perform(put(url, languageGradeRequest, token))
                .andExpect(status().isOk());
    }
    @Test
    void validResquestGetLanguageByUserIdEmitterId() throws Exception {
        String token = authUser(userTest1);
        String url = String.format("/users/%s/languageGrades/%s", userTest2.getId(), userTest1.getId());

        mockMvc.perform(get(url, token))
                .andExpect(status().isOk());
    }
    
}
