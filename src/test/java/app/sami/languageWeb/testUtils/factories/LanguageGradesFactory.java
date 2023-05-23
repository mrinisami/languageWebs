package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.language.models.LanguageGrades;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;

import java.util.UUID;


public class LanguageGradesFactory {

    public static LanguageGrades generateFromUsers(){

        return LanguageGrades.builder()
                .grade(Randomize.grade())
                .emitterUserId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .refLanguage(Randomize.language())
                .build();
    }
}
