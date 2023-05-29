package app.sami.languageWeb.language;

import app.sami.languageWeb.language.models.Language;

public interface GradeStatsSummary {

    Double getEvalGrade();
    Double getUserGrade();
    Double getSelfGrade();
    Language getLanguage();
    Language getTranslatedLanguage();
}
