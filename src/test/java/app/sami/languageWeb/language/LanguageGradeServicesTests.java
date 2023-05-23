package app.sami.languageWeb.language;

import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.mapper.LanguageRequestMapper;
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
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LanguageGradeServicesTests extends IntegrationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageGradesService languageGradesService;

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
    void setup() {
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
    }

    @Test
    void givenUnauthorizedUserEditLanguage_ThrowsUnauthorizedError() {
        languageGradesTest1.setGrade(55.0);
        LanguageGradeRequest languageGradeRequest = LanguageRequestMapper.toLanguageGradeRequest(languageGradesTest1);
        String userEmail = userEvaluator.getEmail();
        assertThrows(UserNotAllowedException.class,
                () -> languageGradesService.editUserLanguageGrade(languageGradeRequest, userEmail, languageGradesTest1.getId()));
    }

    @Test
    void matchingLanguageGradesAfterEdit_ReturnsTrue(){
        languageGradesTest1.setGrade(55.0);
        LanguageGrades expected = languageGradesTest1;
        LanguageGradeRequest languageGradeRequest = LanguageRequestMapper.toLanguageGradeRequest(languageGradesTest1);
        String userEmail = userTest2.getEmail();
        LanguageGrades result = languageGradesService.editUserLanguageGrade(languageGradeRequest, userEmail,
                languageGradesTest1.getId());

        assertThat(result).isEqualTo(expected);
    }

}
