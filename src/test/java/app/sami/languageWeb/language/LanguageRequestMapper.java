package app.sami.languageWeb.language;

import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.LanguageGrades;

public class LanguageRequestMapper {

    public static LanguageGradeRequest toLanguageGradeRequest(LanguageGrades languageGrades){
        return LanguageGradeRequest
                .builder()
                .userId(languageGrades.getUserId())
                .emitterUserId(languageGrades.getEmitterUserId())
                .language(languageGrades.getRefLanguage())
                .id(languageGrades.getId())
                .grade(languageGrades.getGrade())
                .build();
    }
}
