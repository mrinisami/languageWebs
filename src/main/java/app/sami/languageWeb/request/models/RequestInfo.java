package app.sami.languageWeb.request.models;

import app.sami.languageWeb.language.models.Language;

import java.time.Instant;

public interface RequestInfo {
    Long getId();
    Language getSourceLanguage();
    Language getTranslatedLanguage();
    Status getStatus();
    Long getContentId();
    Instant getModifiedAt();
    Double getPrice();
    String getName();
}
