package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LanguageAllGradesDto {
    private Double userGrade;
    private Double evaluatorGrade;
    private Double selfAssessment;
    private Language language;
    private Language translatedLanguage;
}
