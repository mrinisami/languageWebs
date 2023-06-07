package app.sami.languageWeb.request.models;

import app.sami.languageWeb.language.models.Language;

public interface RequestLanguageStats {
    Language getSourceLanguage();
    Language getTranslatedLanguage();
    Integer getNbRequests();
}
