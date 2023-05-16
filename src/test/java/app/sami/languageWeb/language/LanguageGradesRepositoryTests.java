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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LanguageGradesRepositoryTests {

    @Autowired
    private LanguageGradesRepository languageGradesRepository;

    @BeforeEach
    void setup(){
        languageGradesRepository.deleteAll();
    }
    @Autowired
    private UserRepository userRepository;


    @Test
    void matchingAvgGradeEvaluator_ReturnsTrue(){

        User user1 = userRepository.save(UserFactory.userGenerator());
        User evaluator = userRepository.save(UserFactory.evaluatorGenerator());

        double grade1 = 5;
        double grade2 = 10;

        LanguageGrades languageGrades1 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), evaluator.getId(), grade1, Language.ARABIC);
        LanguageGrades languageGrades2 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), evaluator.getId(), grade2, Language.ARABIC);

        languageGradesRepository.save(languageGrades1);
        languageGradesRepository.save(languageGrades2);

        IGradeStats gradeStats = languageGradesRepository.gradeStatsByEvaluator(user1.getId(), Language.ARABIC.toString());
        Double grade = gradeStats.getAvgGrade();
        double expectedGrade = (grade1 + grade2) / 2;

        assertThat(grade).isEqualTo(expectedGrade);
    }

    @Test
    void matchingAvgUserGrade_ReturnsTrue(){
        User user1 = userRepository.save(UserFactory.userGenerator());
        User user2 = userRepository.save(UserFactory.userGenerator());

        double grade1 = 5;
        double grade2 = 10;
        int expectedCount = 2;
        LanguageGrades languageGrades1 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), user2.getId(), grade1, Language.ENGLISH);
        LanguageGrades languageGrades2 = LanguageGradesFactory.generateFromUsers(
                user1.getId(), user2.getId(), grade2, Language.ENGLISH);

        languageGradesRepository.save(languageGrades1);
        languageGradesRepository.save(languageGrades2);

        IGradeStats gradeStats = languageGradesRepository.userGradeStatsByUsers(user1.getId(), Language.ENGLISH.toString());
        Double grade = gradeStats.getAvgGrade();
        Integer count = gradeStats.getGradeCount();
        double expectedGrade = (grade1 + grade2) / 2;

        assertThat(grade).isEqualTo(expectedGrade);
        assertThat(count).isEqualTo(expectedCount);
    }

}
