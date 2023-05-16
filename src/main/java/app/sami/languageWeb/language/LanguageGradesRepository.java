package app.sami.languageWeb.language;

import app.sami.languageWeb.language.models.LanguageGrades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LanguageGradesRepository extends JpaRepository<LanguageGrades, Long> {

    @Query(value = "" +
            "SELECT language " +
            "FROM language_grades " +
            "WHERE user_id = :userId " +
            "AND emitter_user_id = :userId",
    nativeQuery = true)
    List<LanguageGrades> findUniqueUserLanguages(@Param("userId") UUID userId);

    @Query(value = "" +
            "SELECT AVG(lg.grade) AS avgGrade, COUNT(lg.grade) AS gradeCount " +
            "FROM language_grades as lg " +
            "JOIN users as emitting_user " +
            "on lg.emitter_user_id = emitting_user.id " +
            "WHERE emitting_user.user_role = 'EVALUATOR' " +
            "AND lg.user_id = :userId " +
            "AND lg.ref_language = :language",
    nativeQuery = true)
    IGradeStats gradeStatsByEvaluator(@Param("userId") UUID userId, @Param("language") String language);

    @Query(value = "" +
            "SELECT grade " +
            "FROM language_grades " +
            "WHERE emitter_user_id = user_id " +
            "AND user_id = :userId " +
            "AND ref_language = :language",
    nativeQuery = true)
    Double selfAssessmentGrade(@Param("userId") UUID userId, @Param("language") String language);

    @Query(value = "" +
            "SELECT AVG(lg.grade) AS avgGrade, COUNT(lg.grade) AS gradeCount " +
            "FROM language_grades AS lg " +
            "WHERE emitter_user_id != user_id " +
            "AND user_id = :userId " +
            "AND ref_language = :language",
            nativeQuery = true)
    IGradeStats userGradeStatsByUsers(@Param("userId") UUID userId, @Param("language") String language);

}
