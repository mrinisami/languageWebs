package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LanguageGradeRequest {
    private Double grade;
    private Language refLanguage;
    private Language translatedLanguage;
}
