package app.sami.languageWeb.language.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguagesGradesEmitterDto {
    private List<LanguageGradesEmitterDto> languageGradesEmitterDtos;
}
