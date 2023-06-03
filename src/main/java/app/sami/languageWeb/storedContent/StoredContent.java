package app.sami.languageWeb.storedContent;
import app.sami.languageWeb.language.models.Language;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "stored_content")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@With
public class StoredContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language sourceLanguage;
    @Enumerated(EnumType.STRING)
    private Language translatedLanguage;

    private String name;
    @CreatedDate
    private Instant createdAt;

    @CreatedDate
    private Instant modifiedAt;
    private UUID userId;
}
