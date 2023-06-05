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
    private String name;
    private Long id;
}
