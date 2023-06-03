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

import java.util.ArrayList;
import java.util.List;

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
    private LanguageGrades languageGradesTest3;
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
                .withRefLanguage(Language.ARABIC)
                .withTranslatedLanguage(Language.ENGLISH));
        languageGradesTest2 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                .withTranslatedLanguage(Language.ENGLISH));
        languageGradesTest3 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ENGLISH)
                .withTranslatedLanguage(Language.FULA));
    }

    @Test
    void givenUnauthorizedUserEditLanguage_ThrowsUnauthorizedError() {
        languageGradesTest1.setGrade(55.0);
        LanguageGradeRequest languageGradeRequest = LanguageRequestMapper.toLanguageGradeRequest(languageGradesTest1);
        assertThrows(UserNotAllowedException.class,
                () -> languageGradesService.editUserLanguageGrade(languageGradeRequest, userEvaluator.getId(), languageGradesTest1.getId()));
    }

    @Test
    void matchingLanguageGradesAfterEdit_ReturnsTrue(){
        languageGradesTest1.setGrade(55.0);
        LanguageGrades expected = languageGradesTest1;
        LanguageGradeRequest languageGradeRequest = LanguageRequestMapper.toLanguageGradeRequest(languageGradesTest1);
        LanguageGrades result = languageGradesService.editUserLanguageGrade(languageGradeRequest, userTest2.getId(),
                languageGradesTest1.getId());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void matchingLanguageGradesByEmitterAndUser_ReturnsTrue(){
        List<LanguageGrades> expected = new ArrayList<>();
        expected.add(languageGradesTest2);
        expected.add(languageGradesTest3);

        List<LanguageGrades> result = languageGradesRepository.findByUserIdAndEmitterUserId(userTest1.getId(),
                userTest3.getId());

        assertThatList(result).hasSameElementsAs(expected);
    }

    @Test
    void givenEmptyRepoLanguageGradesByEmitterAndUser_ReturnsEmpty(){
        List<LanguageGrades> result = languageGradesRepository.findByUserIdAndEmitterUserId(userTest1.getId(), userEvaluator.getId());

        assertThatList(result).isEmpty();
    }
}
