package app.sami.languageWeb.testUtils;

import app.sami.languageWeb.language.models.Language;

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
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    public static double grade(){
        return ThreadLocalRandom.current().nextDouble(0, 10);
    }

    public static Language language(){
        Language[] languages = Language.values();
        return languages[new Random().nextInt(languages.length)];
    }
}
