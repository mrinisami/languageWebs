package app.sami.languageWeb.text;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity(name = "texts")
@Table
@NoArgsConstructor
@Data
public class Text {
    @Id
    @GeneratedValue
    private UUID id;

    private String textContent;
    private String textLink;
    private String textLanguage;
    @CreatedDate
    private Instant createdAt;

    private UUID userId;
}
