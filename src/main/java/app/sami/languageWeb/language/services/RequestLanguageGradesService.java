package app.sami.languageWeb.language.services;

import app.sami.languageWeb.error.exceptions.NotFoundException;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.repos.Language.LanguageGradesRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Builder
public class RequestLanguageGradesService {
    private final LanguageGradesRepository languageGradesRepository;

    public List<LanguageGrades> userLanguageGrades(UUID userId){
        List<LanguageGrades> languageGrades = languageGradesRepository.findAllByUserId(userId);

        return languageGrades;
    }
}
