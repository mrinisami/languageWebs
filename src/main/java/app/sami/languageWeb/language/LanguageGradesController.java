package app.sami.languageWeb.language;

import app.sami.languageWeb.language.dtos.LanguageGradesDto;
import app.sami.languageWeb.language.dtos.LanguagesGradesDto;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.repos.Language.LanguageGradesRepository;
import app.sami.languageWeb.language.services.RequestLanguageGradesService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class LanguageGradesController {

    private RequestLanguageGradesService requestLanguageGradesService;

    @GetMapping("/:userId/languages")
    public LanguagesGradesDto getUserLanguages(@PathVariable UUID userId){
        LanguagesGradesDto languagesGradesDto = new LanguagesGradesDto();
        languagesGradesDto.setLanguageGrades(requestLanguageGradesService.userLanguageGrades(userId)
                .stream().map(this::toLanguageGradesDto).collect(Collectors.toList()));
        return languagesGradesDto;
    }

    private LanguageGradesDto toLanguageGradesDto(LanguageGrades languageGrades){
        LanguageGradesDto languageGradesDto = LanguageGradesDto.builder()
                .adminGrade(languageGrades.getAdminGrade())
                .ownGrade(languageGrades.getOwnGrade())
                .userGrade(languageGrades.getUserGrade())
                .language(languageGrades.getLanguage())
                .build();
       return languageGradesDto;
    }

}
