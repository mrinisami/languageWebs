package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageGradesDto {
    private Language language;
    private Language translatedLanguage;
    private double grade;
}
