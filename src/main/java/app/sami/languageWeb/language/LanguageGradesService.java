package app.sami.languageWeb.language;

import app.sami.languageWeb.error.exceptions.LanguageNotRegisteredException;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import app.sami.languageWeb.user.models.User;
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
        List<LanguageGrades> languageGrades = languageGradesRepository.findUniqueRefLanguageByUserId(userId);


        return languageGrades;
    }

    public List<GradeStatsSummary> getAllUserGrandStats(UUID userId){
        List<GradeStatsSummary> gradeStats = languageGradesRepository.findAllGradeStats(userId);

        return gradeStats;
    }

    public LanguageGrades submitUserLanguageGrade(LanguageGradeRequest languageGradeRequest,
                                                  UUID subject,
                                                  UUID userId){


        if (!subject.equals(userId)){
            LanguageGrades alreadyExistingAssessment = languageGradesRepository.findByUserIdAndRefLanguageAndTranslatedLanguageAndEmitterUserId(
                    userId, languageGradeRequest.getRefLanguage(),
                    languageGradeRequest.getTranslatedLanguage(),
                    userId).orElseThrow(LanguageNotRegisteredException::new);
        }
        LanguageGrades languageGrades = LanguageGrades.builder()
                .userId(userId)
                .refLanguage(languageGradeRequest.getRefLanguage())
                .translatedLanguage(languageGradeRequest.getTranslatedLanguage())
                .emitterUserId(subject)
                .grade(languageGradeRequest.getGrade())
                .build();
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public LanguageGrades editUserLanguageGrade(LanguageGradeRequest languageGradeRequest, UUID subject,
                                                Long id){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        if (!(subject.equals(languageGrades.getEmitterUserId()))) throw new UserNotAllowedException();
        languageGrades.setGrade(languageGradeRequest.getGrade());
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public void deleteUserLanguageGrade(long id){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        languageGradesRepository.delete(languageGrades);
    }

    public List<LanguageGrades> getByUserIdAndEmitterId(UUID userId, UUID subject){
        List<LanguageGrades> languageGrades = languageGradesRepository.findByUserIdAndEmitterUserId(userId, subject);

        return languageGrades;
    }

}
