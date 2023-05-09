package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.language.models.LanguageGrades;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LanguagesGradesDto {
    private List<LanguageGradesDto> languageGrades;
}
