package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterDto {
    private List<Language> sourceLanguages;
    private List<Language> translatedLanguages;
    private Double priceGt;
    private Double priceLt;
    private UUID userId;
}
