package app.sami.languageWeb.language;

import app.sami.languageWeb.language.dtos.*;
import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.spring.binds.RequestJwtSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @PostMapping("/users/{userId}/languages/grade")
    public LanguageGradesDto createUserLanguageGrade(@RequestJwtSubject String subject,
                                                     @RequestBody LanguageGradeRequest languageGradeRequest,
                                                     @PathVariable UUID userId
                                                     ){
            LanguageGradesDto languageGradesDto = toLanguageGradesDto
                    (languageGradesService.submitUserLanguageGrade(languageGradeRequest, subject, userId));
            return languageGradesDto;
    }

    @GetMapping("/public/users/{userId}/languageGrades/summary")
    public LanguagesAllGradesDto getUserLanguagesGrades(@PathVariable UUID userId){
        List<GradeStatsSummary> gradeStats = languageGradesService.getAllUserGrandStats(userId);
        List<LanguageAllGradesDto> languageGrades = gradeStats.stream().map(this::toLanguageAllGradesDto).collect(Collectors.toList());

        return new LanguagesAllGradesDto(languageGrades);
    }

    @PutMapping("/users/{userId}/languageGrade/{languageGradeId}")
    public LanguageGradesDto editLanguageGrade(@RequestJwtSubject String subject,
                                               @RequestBody LanguageGradeRequest languageGradeRequest,
                                               @PathVariable UUID userId,
                                               @PathVariable Long languageGradeId){

        return toLanguageGradesDto(languageGradesService.editUserLanguageGrade(languageGradeRequest, subject, languageGradeId));
    }

    @GetMapping("/users/{userId}/languageGrades/{emitterUserId}")
    public LanguagesGradesEmitterDto getLanguageGradesFromEmitterAndUser(@RequestJwtSubject String subject,
                                                                         @PathVariable UUID userId,
                                                                         @PathVariable UUID emitterUserId){
        List<LanguageGradesEmitterDto> languageGradesEmitterDtos = languageGradesService.getByUserIdAndEmitterId(userId, subject)
                .stream().map(this::toLanguageGradeIdDto).collect(Collectors.toList());

        return new LanguagesGradesEmitterDto(languageGradesEmitterDtos);
    }
    private LanguageGradesDto toLanguageGradesDto(LanguageGrades languageGrades){
        LanguageGradesDto languageGradesDto = LanguageGradesDto.builder()
                .grade(languageGrades.getGrade())
                .language(languageGrades.getRefLanguage())
                .translatedLanguage(languageGrades.getTranslatedLanguage())
                .build();
       return languageGradesDto;
    }

    private LanguageGradesEmitterDto toLanguageGradeIdDto(LanguageGrades languageGrades){
        return LanguageGradesEmitterDto.builder()
                .id(languageGrades.getId())
                .grade(languageGrades.getGrade())
                .language(languageGrades.getRefLanguage())
                .translatedLanguage(languageGrades.getTranslatedLanguage())
                .build();
    }

    private LanguageAllGradesDto toLanguageAllGradesDto(GradeStatsSummary gradeStats){
        return LanguageAllGradesDto.builder()
                .evaluatorGrade(gradeStats.getEvalGrade())
                .userGrade(gradeStats.getUserGrade())
                .selfAssessment(gradeStats.getSelfGrade())
                .language(gradeStats.getLanguage())
                .translatedLanguage(gradeStats.getTranslatedLanguage())
                .build();
    }



}
