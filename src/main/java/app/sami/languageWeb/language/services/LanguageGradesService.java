package app.sami.languageWeb.language.services;

import app.sami.languageWeb.error.exceptions.LanguageNotRegisteredException;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.error.exceptions.UserNotAllowedException;
import app.sami.languageWeb.language.IAllGradeStats;
import app.sami.languageWeb.language.IGradeStats;
import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.LanguageGradesRepository;
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

    public IGradeStats getUserGradeStats (UUID userId, Language language){
        IGradeStats gradeStats = languageGradesRepository.userGradeStatsByUsers(userId, language.toString());
        if (gradeStats.getGradeCount() == 0) throw new NotFoundException();

        return gradeStats;
    }

    public IGradeStats getUserGradeStatsByEvaluator (UUID userId, Language language){
        IGradeStats gradeStats = languageGradesRepository.gradeStatsByEvaluator(userId, language.toString());
        if (gradeStats.getGradeCount() == 0) throw new NotFoundException();

        return gradeStats;
    }

    public List<IAllGradeStats> getAllUserGrandStats(UUID userId){
        List<IAllGradeStats> gradeStats = languageGradesRepository.findAllGradeStats(userId);

        if (gradeStats.isEmpty()){
            throw new NotFoundException();
        }
        return gradeStats;
    }
    public Double getSelfAssessment(UUID userId, Language language){
        return languageGradesRepository.selfAssessmentGrade(userId, language.toString())
                .orElseThrow(NotFoundException::new);
    }

    public LanguageGrades submitUserLanguageGrade(LanguageGradeRequest languageGradeRequest){
        LanguageGrades alreadyExistingAssessment = languageGradesRepository.findByUserIdAndRefLanguageAndEmitterUserId(
                languageGradeRequest.getUserId(), languageGradeRequest.getLanguage(),
                languageGradeRequest.getUserId()).orElseThrow(LanguageNotRegisteredException::new);

        LanguageGrades languageGrades = LanguageGrades.builder()
                .userId(languageGradeRequest.getUserId())
                .refLanguage(languageGradeRequest.getLanguage())
                .emitterUserId(languageGradeRequest.getEmitterUserId())
                .grade(languageGradeRequest.getGrade())
                .build();
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public LanguageGrades editUserLanguageGrade(LanguageGradeRequest languageGradeRequest, String subject){
        LanguageGrades languageGrades = languageGradesRepository.findById(languageGradeRequest.getId())
                .orElseThrow(NotFoundException::new);
        User user = userRepository.findByEmail(subject).orElseThrow(NotFoundException::new);
        if (user.getId() != languageGrades.getEmitterUserId()) throw new UserNotAllowedException();
        languageGrades.setGrade(languageGradeRequest.getGrade());
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public void deleteUserLanguageGrade(long id){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        languageGradesRepository.delete(languageGrades);
    }


}
