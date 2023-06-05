package app.sami.languageWeb.testUtils;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.models.Status;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Randomize {
    public final static String EMAIL_DOMAIN = "@languageWeb.app";

    public static String string(String prefix){
        return String.format("%s__%s", prefix, UUID.randomUUID());
    }
    public static String email(){
        return String.format("%s%s", UUID.randomUUID(), EMAIL_DOMAIN);
    }

    public static String name(){
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static Double grade(){
        return ThreadLocalRandom.current().nextDouble(0, 100);
    }

    public static Language language(){
        Language[] languages = Language.values();
        return languages[new Random().nextInt(languages.length)];
    }

    public static Status status(){
        Status[] statuses = Status.values();
        return statuses[new Random().nextInt(statuses.length)];
    }
}
