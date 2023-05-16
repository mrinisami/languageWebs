package app.sami.languageWeb.language;

import app.sami.languageWeb.language.dtos.LanguageGradesStatsDto;
import app.sami.languageWeb.language.dtos.LanguageGradesDto;
import app.sami.languageWeb.language.dtos.LanguagesGradesDto;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.services.LanguageGradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/users")
public class LanguageGradesController {

    @Autowired
    private LanguageGradesService languageGradesService;

    @GetMapping("/{userId}/languages")
    public LanguagesGradesDto getUserLanguages(@PathVariable UUID userId){
        LanguagesGradesDto languagesGradesDto = new LanguagesGradesDto();
        languagesGradesDto.setLanguageGrades(languageGradesService.requestUserLanguageGrades(userId)
                .stream().map(this::toLanguageGradesDto).collect(Collectors.toList()));
        return languagesGradesDto;
    }

    @GetMapping("/:userId/:language/user-grade")
    public LanguageGradesStatsDto getAvgUserGrade(@PathVariable UUID userId, @PathVariable String language){
        IGradeStats gradeStats = languageGradesService.getUserGradeStats(userId, Language.valueOf(language));
        LanguageGradesStatsDto languageGradesStatsDto = LanguageGradesStatsDto.builder()
                .language(Language.valueOf(language))
                .avgGrade(gradeStats.getAvgGrade())
                .gradeCount(gradeStats.getGradeCount())
                .build();
        return languageGradesStatsDto;
    }

    @GetMapping("/:userId/:language/evaluator-grade")
    public LanguageGradesStatsDto getUserGradeStatsByEvaluator(@PathVariable UUID userId, @PathVariable String language){
        IGradeStats gradeStats = languageGradesService.getUserGradeStatsByEvaluator(userId, Language.valueOf(language));
        LanguageGradesStatsDto languageGradesStatsDto = LanguageGradesStatsDto.builder()
                .language(Language.valueOf(language))
                .avgGrade(gradeStats.getAvgGrade())
                .gradeCount(gradeStats.getGradeCount())
                .build();
        return languageGradesStatsDto;
    }
    private LanguageGradesDto toLanguageGradesDto(LanguageGrades languageGrades){
        LanguageGradesDto languageGradesDto = LanguageGradesDto.builder()
                .grade(languageGrades.getGrade())
                .language(languageGrades.getRefLanguage())
                .build();
       return languageGradesDto;
    }



}
