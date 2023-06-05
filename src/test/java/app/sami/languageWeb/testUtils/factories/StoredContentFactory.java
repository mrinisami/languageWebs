package app.sami.languageWeb.testUtils.factories;

import app.sami.languageWeb.storedContent.StoredContent;
import app.sami.languageWeb.testUtils.Randomize;
import app.sami.languageWeb.storedContent.dtos.StoredContentDto;

public class StoredContentFactory {

    public static StoredContentDto storedContentDtoGenerator(){
        return StoredContentDto.builder()
                .sourceLanguage(Randomize.language())
                .name(Randomize.string("file"))
                .build();
    }

    public static StoredContent storedContentGenerator(){
        return StoredContent.builder()
                .sourceLanguage(Randomize.language())
                .name(Randomize.string("test__"))
                .build();
    }
}
