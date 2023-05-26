package app.sami.languageWeb.language;

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

    Optional<LanguageGrades> findByUserIdAndRefLanguageAndTranslatedLanguageAndEmitterUserId(UUID userId,
                                                                                             Language language,
                                                                                             Language translatedLanguage,
                                                                                             UUID emitterUserId);
    List<LanguageGrades> findUniqueRefLanguageByUserId(UUID userId);

    List<LanguageGrades> findByUserIdAndEmitterUserId(UUID userId, UUID emitterUserId);

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
            "COALESCE(sa.ref_language, ue.ref_language, et.ref_language) AS language " +
            "FROM self_assessment as sa " +
            "left JOIN usereval AS ue " +
            "ON sa.ref_language = ue.ref_language " +
            "left JOIN evaltable AS et " +
            "ON sa.ref_language = et.ref_language",
            nativeQuery = true)
    List<GradeStatsSummary> findAllGradeStats(@Param("userId") UUID userId);


}
