package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLanguageStatsDto {
    Language sourceLanguage;
    Language translatedLanguage;
    Integer nbRequests;
}
