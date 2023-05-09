package app.sami.languageWeb.language.repos.Language;

import app.sami.languageWeb.language.models.LanguageGrades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageGradesRepository extends JpaRepository<LanguageGrades, Long> {

    List<LanguageGrades> findAllByUserId(UUID userId);
}
