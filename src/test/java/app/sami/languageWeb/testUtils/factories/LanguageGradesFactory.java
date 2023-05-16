package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;

import java.util.UUID;

public class LanguageGradesFactory {

    public static LanguageGrades generateFromUsers(UUID reviewedId, UUID reviewerId, double grade, Language language){

        return LanguageGrades.builder()
                .grade(grade)
                .emitterUserId(reviewerId)
                .userId(reviewedId)
                .refLanguage(language)
                .build();
    }
}
