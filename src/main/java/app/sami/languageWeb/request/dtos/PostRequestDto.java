package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class PostRequestDto {
    private Double price;
    private Language sourceLanguage;
    private Language translatedLanguage;
    private String name;
    private String filePath;
    private String description;
    private Long dueDate;
}
