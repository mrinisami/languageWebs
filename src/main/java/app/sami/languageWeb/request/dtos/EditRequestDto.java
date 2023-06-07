package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class EditRequestDto {
    private Double price;
    private Long dueDate;
}
