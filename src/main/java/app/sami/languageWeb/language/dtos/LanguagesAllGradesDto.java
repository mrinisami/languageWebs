package app.sami.languageWeb.language.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LanguagesAllGradesDto {
    private List<LanguageAllGradesDto> languages;
}
