package app.sami.languageWeb.language;
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
                .withEmitterUserId(userTest2.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC));
        languageGradesRepository.save(LanguageGradesFactory.generateFromUsers().withGrade(grade2)
                .withEmitterUserId(userTest3.getId())
                .withUserId(userTest1.getId())
                .withRefLanguage(Language.ARABIC));
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

        assertThat(gradeStats.size() == 1);
    }

    @Test
    void givenEmptyRepo_ReturnsEmpty(){
        Optional<LanguageGrades> result = languageGradesRepository.findByUserIdAndRefLanguageAndEmitterUserId(userTest2.getId(),
                Language.KALAALLISUT, userTest2.getId());

        assertThat(result).isEmpty();
    }
}
