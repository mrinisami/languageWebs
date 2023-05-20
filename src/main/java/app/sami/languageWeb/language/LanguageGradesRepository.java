package app.sami.languageWeb.language;

import app.sami.languageWeb.IAllGradeStats;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageGradesRepository extends JpaRepository<LanguageGrades, Long> {

    Optional<LanguageGrades> findByUserId(UUID userId);

    Optional<LanguageGrades> findByUserIdAndRefLanguageAndEmitterUserId(UUID userId, Language language, UUID emitterUserId);
    List<LanguageGrades> findUniqueRefLanguageByUserId(UUID userId);

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
            "WITH evaltable AS " +
            "(SELECT  AVG(lg.grade) AS eval_grade, lg.ref_language " +
            "FROM language_grades AS lg " +
            "JOIN users AS u " +
            "ON lg.emitter_user_id = u.id " +
            "WHERE u.user_role = 'EVALUATOR' " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language), " +
            "usereval AS " +
            "(SELECT AVG(lg.grade) AS user_grade, lg.ref_language " +
            "FROM language_grades AS lg " +
            "WHERE lg.emitter_user_id != lg.user_id " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language), " +
            "self_assessment AS (" +
            "SELECT grade AS self_grade, ref_language " +
            "FROM language_grades " +
            "WHERE user_id = emitter_user_id " +
            "AND user_id = :userId) " +
            "SELECT et.eval_grade AS evalGrade, ue.user_grade AS userGrade, sa.self_grade AS selfGrade, " +
            "sa.ref_language AS language " +
            "FROM evaltable AS et " +
            "FULL OUTER JOIN usereval AS ue " +
            "ON et.ref_language = ue.ref_language " +
            "full OUTER JOIN self_assessment AS sa " +
            "ON et.ref_language = sa.ref_language",
            nativeQuery = true)
    List<IAllGradeStats> findAllGradeStats(@Param("userId") UUID userId);

    @Query(value = "" +
            "SELECT grade " +
            "FROM language_grades " +
            "WHERE emitter_user_id = user_id " +
            "AND user_id = :userId " +
            "AND ref_language = :language",
    nativeQuery = true)
    Optional<Double> selfAssessmentGrade(@Param("userId") UUID userId, @Param("language") String language);

    @Query(value = "" +
            "SELECT AVG(lg.grade) AS avgGrade, COUNT(lg.grade) AS gradeCount " +
            "FROM language_grades AS lg " +
            "WHERE emitter_user_id != user_id " +
            "AND user_id = :userId " +
            "AND ref_language = :language",
            nativeQuery = true)
    IGradeStats userGradeStatsByUsers(@Param("userId") UUID userId, @Param("language") String language);

}
