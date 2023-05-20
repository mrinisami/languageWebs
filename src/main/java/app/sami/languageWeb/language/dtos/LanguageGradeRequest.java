package app.sami.languageWeb.language.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class LanguageGradeRequest {
    UUID userId;
    UUID emitterUserId;
    Double grade;
    Language language;
}
