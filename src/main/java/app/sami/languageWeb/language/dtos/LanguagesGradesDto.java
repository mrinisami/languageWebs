package app.sami.languageWeb.language.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LanguagesGradesDto {
    private List<LanguageGradesDto> languageGrades;
}
