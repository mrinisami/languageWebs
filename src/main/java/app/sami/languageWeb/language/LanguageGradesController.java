package app.sami.languageWeb.language;

import app.sami.languageWeb.IAllGradeStats;
import app.sami.languageWeb.auth.Role;
import app.sami.languageWeb.error.exceptions.LanguageNotRegisteredException;
import app.sami.languageWeb.language.dtos.*;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.language.services.LanguageGradesService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static app.sami.languageWeb.auth.Role.EVALUATOR;

@RestController
public class LanguageGradesController {

    @Autowired
    private LanguageGradesService languageGradesService;

    @GetMapping("/public/users/{userId}/languages")
    public LanguagesGradesDto getUserLanguages(@PathVariable UUID userId){
        LanguagesGradesDto languagesGradesDto = new LanguagesGradesDto();
        languagesGradesDto.setLanguageGrades(languageGradesService.requestUserLanguageGrades(userId)
                .stream().map(this::toLanguageGradesDto).collect(Collectors.toList()));
        return languagesGradesDto;
    }

    @GetMapping("/public/users/{userId}/{language}/user-grade")
    public LanguageGradesStatsDto getAvgUserGrade(@PathVariable UUID userId, @PathVariable String language){
        IGradeStats gradeStats = languageGradesService.getUserGradeStats(userId, Language.valueOf(language));
        LanguageGradesStatsDto languageGradesStatsDto = LanguageGradesStatsDto.builder()
                .language(Language.valueOf(language))
                .avgGrade(gradeStats.getAvgGrade())
                .gradeCount(gradeStats.getGradeCount())
                .build();
        return languageGradesStatsDto;
    }

    @PostMapping("/users/create-rating")
    @RolesAllowed({Role.Raw.EVALUATOR, Role.Raw.USER})
    public LanguageGradesDto createUserLanguageGrade(@RequestBody LanguageGradeRequest languageGradeRequest){
            LanguageGradesDto languageGradesDto = toLanguageGradesDto
                    (languageGradesService.submitUserLanguageGrade(languageGradeRequest));
            return languageGradesDto;
    }

    @GetMapping("/public/users/{userId}/{language}/evaluator-grade")
    public LanguageGradesStatsDto getUserGradeStatsByEvaluator(@PathVariable UUID userId, @PathVariable String language){
        IGradeStats gradeStats = languageGradesService.getUserGradeStatsByEvaluator(userId, Language.valueOf(language));
        LanguageGradesStatsDto languageGradesStatsDto = LanguageGradesStatsDto.builder()
                .language(Language.valueOf(language))
                .avgGrade(gradeStats.getAvgGrade())
                .gradeCount(gradeStats.getGradeCount())
                .build();
        return languageGradesStatsDto;
    }

    @GetMapping("/public/users/{userId}/languages/grades")
    public LanguagesAllGradesDto getUserLanguagesGrades(@PathVariable UUID userId){
        List<IAllGradeStats> gradeStats = languageGradesService.getAllUserGrandStats(userId);
        List<LanguageAllGradesDto> languageGrades = gradeStats.stream().map(this::toLanguageAllGradesDto).collect(Collectors.toList());

        return new LanguagesAllGradesDto(languageGrades);
    }


    private LanguageGradesDto toLanguageGradesDto(LanguageGrades languageGrades){
        LanguageGradesDto languageGradesDto = LanguageGradesDto.builder()
                .grade(languageGrades.getGrade())
                .language(languageGrades.getRefLanguage())
                .build();
       return languageGradesDto;
    }

    private LanguageAllGradesDto toLanguageAllGradesDto(IAllGradeStats gradeStats){
        return LanguageAllGradesDto.builder()
                .evaluatorGrade(gradeStats.getEvalGrade())
                .userGrade(gradeStats.getUserGrade())
                .selfAssessment(gradeStats.getSelfGrade())
                .language(gradeStats.getLanguage())
                .build();
    }



}
