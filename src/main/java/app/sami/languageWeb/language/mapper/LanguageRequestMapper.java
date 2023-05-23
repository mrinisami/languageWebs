package app.sami.languageWeb.language.mapper;

import app.sami.languageWeb.language.dtos.LanguageGradeRequest;
import app.sami.languageWeb.language.models.LanguageGrades;

public class LanguageRequestMapper {

    public static LanguageGradeRequest toLanguageGradeRequest(LanguageGrades languageGrades){
        return new LanguageGradeRequest(languageGrades.getGrade());
    }
}
