package app.sami.languageWeb.language.services;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.repos.Language.LanguageGradesRepository;
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
        List<LanguageGrades> languageGrades = languageGradesRepository.findAllByUserId(userId);

        return languageGrades;
    }


    public LanguageGrades submitUserLanguageGrade(UUID emitterUserId, UUID userId, Language language, double grade){
        LanguageGrades languageGrades = LanguageGrades.builder()
                .userId(userId)
                .language(language)
                .emitterUserId(emitterUserId)
                .build();
        setGradePerRole(languageGrades, emitterUserId, grade);

        return languageGrades;
    }

    public LanguageGrades editUserLanguageGrade(long id, UUID emitterUserId, double grade){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        setGradePerRole(languageGrades, emitterUserId, grade);
        languageGradesRepository.save(languageGrades);

        return languageGrades;
    }

    public void deleteUserLanguageGrade(long id, UUID emitterUserId){
        LanguageGrades languageGrades = languageGradesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        languageGradesRepository.delete(languageGrades);
    }

    private void setGradePerRole(LanguageGrades languageGrades, UUID emitterUserId, double grade){
        if (emitterUserId.equals(languageGrades.getUserId())){
            languageGrades.setOwnGrade(grade);
        }
        else if (userRepository.findById(emitterUserId).orElseThrow(NotFoundException::new)
                .getUserRole()
                .equals(Role.ADMIN)){
            languageGrades.setAdminGrade(grade);
        }
        else {
            languageGrades.setUserGrade(grade);
        };
    }
}
