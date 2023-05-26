package app.sami.languageWeb.language.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
@Data
@Table
@Entity(name = "language_grades")
public class LanguageGrades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userId;
    private UUID emitterUserId;

    @Enumerated(EnumType.STRING)
    private Language refLanguage;
    @Enumerated(EnumType.STRING)
    private Language translatedLanguage;
    private Double grade;
}
