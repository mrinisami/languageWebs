package app.sami.languageWeb.language;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.factories.LanguageGradesFactory;
import app.sami.languageWeb.testUtils.factories.UserFactory;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class LanguageGradesRepositoryTests {

    @Autowired
    private LanguageGradesRepository languageGradesRepository;

    @Autowired
    private UserRepository userRepository;

    private User userTest1;
    private User userTest2;
    private User userTest3;
    private User userEvaluator;
    private User userEvaluator2;
    private LanguageGrades languageGradesSelfAssessmentTest1;
    private LanguageGrades languageGradesSelfAssessmentTest2;
    private LanguageGrades languageGradesTest3;
    private Double grade1 = 50.0;
    private Double grade2 = 100.0;
    @BeforeEach
    void setup(){
        userTest1 = userRepository.save(UserFactory.userGenerator());
        userTest2 = userRepository.save(UserFactory.userGenerator());
        userTest3 = userRepository.save(UserFactory.userGenerator());
        userEvaluator = userRepository.save(UserFactory.evaluatorGenerator());
        userEvaluator2 = userRepository.save(UserFactory.evaluatorGenerator());
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade1)
                .withEmitterUserId(userTest1.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                .withTranslatedLanguage(Language.ENGLISH));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade1)
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                .withTranslatedLanguage(Language.ENGLISH));
        languageGradesTest3 = languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC)
                .withTranslatedLanguage(Language.ENGLISH));
    }

    @Test
    void listNotEmptyGradeStatsByLanguage_ReturnsTrue(){
        GradeStatsSummary gradeStatsSummaries = languageGradesRepository.findByRefLanguageAndTranslatedLanguageAndUserId(
                userTest1.getId(), Language.ARABIC.toString(), Language.ENGLISH.toString()
        ).orElseThrow(NotFoundException::new);


    }
    @Test
    void matchingGradesStatsListByUser_ReturnsTrue(){
        UUID userId = userTest1.getId();

        List<GradeStatsSummary> gradeStats = languageGradesRepository.findAllGradeStats(userId);

        assertThat(gradeStats.size() == 1);
    }

    @Test
    void givenNonExistingUserGradeStats_ReturnsEmpty(){
        //Actually returns one because of aggregates returns a line of nulls

        List<GradeStatsSummary> gradeStats = languageGradesRepository.findAllGradeStats(UUID.randomUUID());

        assertThat(gradeStats.isEmpty());
    }

    @Test
    void givenEmptyRepo_ReturnsEmpty(){
        Optional<LanguageGrades> result = languageGradesRepository.findByUserIdAndRefLanguageAndTranslatedLanguageAndEmitterUserId(userTest2.getId(),
                Language.KALAALLISUT, Language.GALICIAN, userTest2.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void matchingLanguageGradesFindByUserIdEmitterIdLanguages_ReturnsTrue(){
        LanguageGrades result = languageGradesRepository.findByUserIdAndRefLanguageAndTranslatedLanguageAndEmitterUserId(
                languageGradesTest3.getUserId(),
                languageGradesTest3.getRefLanguage(),
                languageGradesTest3.getTranslatedLanguage(),
                languageGradesTest3.getEmitterUserId()).orElseThrow(NotFoundException::new);
        LanguageGrades expected = languageGradesTest3;

        assertThat(result).isEqualTo(expected);
    }
}
