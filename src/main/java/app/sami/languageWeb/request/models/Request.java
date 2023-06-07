package app.sami.languageWeb.request.models;

import app.sami.languageWeb.language.models.Language;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@With
@Table
@Entity(name = "request")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Language sourceLanguage;
    @Enumerated(EnumType.STRING)
    private Language translatedLanguage;
    private UUID userId;
    private Double price;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
    private Double avgTime;
    private String filePath;
    private String name;
}
