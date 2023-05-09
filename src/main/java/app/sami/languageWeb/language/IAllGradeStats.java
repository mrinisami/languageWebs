package app.sami.languageWeb.language;

import app.sami.languageWeb.language.models.Language;

public interface IAllGradeStats {

    Double getEvalGrade();
    Double getUserGrade();
    Double getSelfGrade();
    Language getLanguage();
}
