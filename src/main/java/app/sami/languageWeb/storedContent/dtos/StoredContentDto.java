package app.sami.languageWeb.storedContent.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class StoredContentDto {
    private Language sourceLanguage;
    private Language translatedLanguage;
    private String name;
}
