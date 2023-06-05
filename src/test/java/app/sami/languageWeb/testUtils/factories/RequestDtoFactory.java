package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.testUtils.Randomize;

public class RequestDtoFactory {
    public static RequestDto generateRequestDto(){
        return RequestDto.builder()
                .price(Randomize.grade())
                .build();
    }

    public static Request generateRequest(){
        return Request.builder()
                .sourceLanguage(Randomize.language())
                .translatedLanguage(Randomize.language())
                .price(Randomize.grade())
                .status(Randomize.status())
                .build();
    }
}
