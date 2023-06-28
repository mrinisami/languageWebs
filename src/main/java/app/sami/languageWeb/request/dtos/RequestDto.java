package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.user.dto.UserDto;
import lombok.*;
import org.checkerframework.checker.units.qual.A;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private Double price;
    private String sourceLanguage;
    private String translatedLanguage;
    private String name;
    private Instant modifiedAt;
    private Status status;
    private Double avgTime;
    private String filePath;
    private String downloadUri;
    private UserDto userDto;
    private Integer nbWords;
    private String description;
    private Instant dueDate;
}
