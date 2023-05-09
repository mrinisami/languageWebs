package app.sami.languageWeb.language.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
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
    private Double grade;
}
