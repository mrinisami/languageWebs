package app.sami.languageWeb.language;

import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.services.LanguageGradesService;
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

import java.util.UUID;

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
        languageGradesTest1 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers(
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
    void givenUnauthorizedUserEditLanguage_ThrowsUnauthorizedError() {
        languageGradesTest1.setGrade(55.0);
        LanguageGradeRequest languageGradeRequest = toLanguageGradeRequest(languageGradesTest1);
        String userEmail = userEvaluator.getEmail();
        assertThrows(UserNotAllowedException.class,
                () -> languageGradesService.editUserLanguageGrade(languageGradeRequest, userEmail));
    }

    @Test
    void matchingLanguageGradesAfterEdit_ReturnsTrue(){
        languageGradesTest1.setGrade(55.0);
        LanguageGrades expected = languageGradesTest1;
        LanguageGradeRequest languageGradeRequest = toLanguageGradeRequest(languageGradesTest1);
        String userEmail = userTest2.getEmail();
        LanguageGrades result = languageGradesService.editUserLanguageGrade(languageGradeRequest, userEmail);

        assertThat(result).isEqualTo(expected);
    }
    private LanguageGradeRequest toLanguageGradeRequest(LanguageGrades languageGrades) {
        return LanguageGradeRequest
                .builder()
                .userId(languageGrades.getUserId())
                .emitterUserId(languageGrades.getEmitterUserId())
                .language(languageGrades.getRefLanguage())
                .id(languageGrades.getId())
                .grade(languageGrades.getGrade())
                .build();
    }
}
