package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LanguageGradesStatsDto {
    private Double avgGrade;
    private Language language;
    private Integer gradeCount;
}
