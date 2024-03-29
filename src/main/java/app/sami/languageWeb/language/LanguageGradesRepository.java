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
    Optional<LanguageGrades> findByRefLanguageAndTranslatedLanguageAndUserId(Language reflanguage,
                                                                             Language translatedLanguage,
                                                                             UUID userId
                                                                             );
    List<LanguageGrades> findByUserIdAndEmitterUserId(UUID userId, UUID emitterUserId);

    @Query(value = "" +
            "WITH evaltable AS " +
            "(SELECT  AVG(lg.grade) AS eval_grade, lg.ref_language, lg.translated_language " +
            "FROM language_grades AS lg " +
            "JOIN users AS u " +
            "ON lg.emitter_user_id = u.id " +
            "WHERE u.user_role = 'EVALUATOR' " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language, lg.translated_language), " +
            "usereval AS " +
            "(SELECT AVG(lg.grade) AS user_grade, lg.ref_language, lg.translated_language " +
            "FROM language_grades AS lg " +
            "WHERE lg.emitter_user_id != lg.user_id " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language, lg.translated_language), " +
            "self_assessment AS (" +
            "SELECT grade AS self_grade, ref_language, translated_language " +
            "FROM language_grades " +
            "WHERE user_id = emitter_user_id " +
            "AND user_id = :userId) " +
            "SELECT et.eval_grade AS evalGrade, ue.user_grade AS userGrade, sa.self_grade AS selfGrade, " +
            "COALESCE(sa.ref_language, ue.ref_language, et.ref_language) AS language, " +
            "COAlESCE(sa.translated_language, ue.translated_language, et.translated_language) AS translatedLanguage " +
            "FROM self_assessment as sa " +
            "left JOIN usereval AS ue " +
            "ON sa.ref_language = ue.ref_language " +
            "AND sa.translated_language = ue.translated_language " +
            "left JOIN evaltable AS et " +
            "ON sa.ref_language = et.ref_language " +
            "AND sa.translated_language = ue.translated_language",
            nativeQuery = true)
    List<GradeStatsSummary> findAllGradeStats(@Param("userId") UUID userId);

    @Query(value = "" +
            "WITH evaltable AS " +
            "(SELECT  AVG(lg.grade) AS eval_grade, lg.ref_language, lg.translated_language " +
            "FROM language_grades AS lg " +
            "JOIN users AS u " +
            "ON lg.emitter_user_id = u.id " +
            "WHERE u.user_role = 'EVALUATOR' " +
            "AND lg.ref_language = :refLanguage " +
            "AND lg.translated_language = :translatedLanguage " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language, lg.translated_language), " +
            "usereval AS " +
            "(SELECT AVG(lg.grade) AS user_grade, lg.ref_language, lg.translated_language " +
            "FROM language_grades AS lg " +
            "WHERE lg.emitter_user_id != lg.user_id " +
            "AND lg.ref_language = :refLanguage " +
            "AND lg.translated_language = :translatedLanguage " +
            "AND lg.user_id = :userId " +
            "GROUP BY lg.ref_language, lg.translated_language), " +
            "self_assessment AS (" +
            "SELECT grade AS self_grade, ref_language, translated_language " +
            "FROM language_grades " +
            "WHERE user_id = emitter_user_id " +
            "AND ref_language = :refLanguage " +
            "AND translated_language = :translatedLanguage " +
            "AND user_id = :userId) " +
            "SELECT et.eval_grade AS evalGrade, ue.user_grade AS userGrade, sa.self_grade AS selfGrade, " +
            "COALESCE(sa.ref_language, ue.ref_language, et.ref_language) AS language, " +
            "COAlESCE(sa.translated_language, ue.translated_language, et.translated_language) AS translatedLanguage " +
            "FROM self_assessment as sa " +
            "left JOIN usereval AS ue " +
            "ON sa.ref_language = ue.ref_language " +
            "AND sa.translated_language = ue.translated_language " +
            "left JOIN evaltable AS et " +
            "ON sa.ref_language = et.ref_language " +
            "AND sa.translated_language = ue.translated_language",
            nativeQuery = true)
    Optional<GradeStatsSummary> findByRefLanguageAndTranslatedLanguageAndUserId(@Param("userId") UUID userId,
                                                                            @Param("refLanguage") String refLanguage,
                                                                            @Param("translatedLanguage") String translatedLanguage);
}
