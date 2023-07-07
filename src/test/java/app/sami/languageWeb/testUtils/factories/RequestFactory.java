package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.Status;
import app.sami.languageWeb.testUtils.Randomize;

import java.util.Random;

public class RequestFactory {
    public static Request generateRequest(){
        return Request.builder()
                .price(Randomize.grade())
                .name(Randomize.name())
                .translatedLanguage(Randomize.language())
                .sourceLanguage(Randomize.language())
                .status(Status.PENDING)
                .build()
;    }
}
