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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void matchingAvgGradeEvaluator_ReturnsTrue(){


        IGradeStats gradeStats = languageGradesRepository.gradeStatsByEvaluator(userTest1.getId(), Language.ENGLISH.toString())
                .orElseThrow(NotFoundException::new);
        Double grade = gradeStats.getAvgGrade();
        double expectedGrade = (grade1 + grade2) / 2;

        assertThat(grade).isEqualTo(expectedGrade);
    }

    @Test
    void givenOneEntry_ReturnsCountOfOne(){
        IGradeStats result = languageGradesRepository.userGradeStatsByUsers(userTest2.getId(),
                Language.ROMANIAN.toString()).orElseThrow(NotFoundException::new);

        assertThat(result.getGradeCount()).isEqualTo(1);
    }

    @Test
    void givenWrongUser_ReturnsNullAvg(){
        IGradeStats result = languageGradesRepository.userGradeStatsByUsers(UUID.randomUUID(),
                Language.ROMANIAN.toString()).orElseThrow(NotFoundException::new);

        assertThat(result.getAvgGrade()).isNull();
    }
    @Test
    void matchingAvgUserGrade_ReturnsTrue(){


        IGradeStats gradeStats = languageGradesRepository.userGradeStatsByUsers(userTest1.getId(), Language.ARABIC.toString())
                .orElseThrow(NotFoundException::new);
        Double grade = gradeStats.getAvgGrade();
        Integer count = gradeStats.getGradeCount();
        double expectedGrade = (grade1 + grade2) / 2;

        assertThat(grade).isEqualTo(expectedGrade);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void matchingLanguageList_ReturnsTrue(){
        List<LanguageGrades> expected = new ArrayList<>();
        expected.add(languageGradesTest2);
        expected.add(languageGradesTest1);

        Optional<List<LanguageGrades>> result = languageGradesRepository.findUserLanguages(userTest2.getId());

        assertThat(result).isEqualTo(expected);

    }

}
