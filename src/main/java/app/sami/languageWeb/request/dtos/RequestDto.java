package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.models.Status;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class RequestDto {
    private Double price;
    private Long contentId;
    private Language sourceLanguage;
    private Language translatedLanguage;
    private String name;
    private Instant modifiedAt;
    private Status status;
}
