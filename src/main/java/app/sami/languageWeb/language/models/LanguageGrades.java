package app.sami.languageWeb.language.models;

import app.sami.languageWeb.user.models.User;
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

    @Enumerated(EnumType.STRING)
    private Language language;
    private Double ownGrade;
    private Double adminGrade;
    private Double userGrade;
}
