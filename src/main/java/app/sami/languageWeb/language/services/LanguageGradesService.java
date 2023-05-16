package app.sami.languageWeb.language.services;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.language.IGradeStats;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.LanguageGradesRepository;
import app.sami.languageWeb.user.repos.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Builder
public class LanguageGradesService {

    private final LanguageGradesRepository languageGradesRepository;
    private final UserRepository userRepository;

    public List<LanguageGrades> requestUserLanguageGrades(UUID userId){
        List<LanguageGrades> languageGrades = languageGradesRepository.findUniqueUserLanguages(userId);

        return languageGrades;
    }

    public IGradeStats getUserGradeStats (UUID userId, Language language){
        return languageGradesRepository.userGradeStatsByUsers(userId, language.toString());
    }

    public IGradeStats getUserGradeStatsByEvaluator (UUID userId, Language language){
        return languageGradesRepository.gradeStatsByEvaluator(userId, language.toString());
    }

    public Double getSelfAssessment(UUID userId, Language language){
        return languageGradesRepository.selfAssessmentGrade(userId, language.toString());
    }

    public LanguageGrades submitUserLanguageGrade(UUID emitterUserId, UUID userId, Language language, double grade){
        LanguageGrades languageGrades = LanguageGrades.builder()
                .userId(userId)
                .refLanguage(language)
                .emitterUserId(emitterUserId)
                .grade(grade)
                .build();
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public LanguageGrades editUserLanguageGrade(long id, double grade){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        languageGrades.setGrade(grade);
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public void deleteUserLanguageGrade(long id){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        languageGradesRepository.delete(languageGrades);
    }


}
