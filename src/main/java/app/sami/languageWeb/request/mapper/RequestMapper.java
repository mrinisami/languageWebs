package app.sami.languageWeb.request.mapper;

import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestInfo;

public class RequestMapper {

    public static Request toRequest(RequestDto requestDto){
        return Request.builder()
                .price(requestDto.getPrice())
                .contentId(requestDto.getContentId())
                .sourceLanguage(requestDto.getSourceLanguage())
                .translatedLanguage(requestDto.getTranslatedLanguage())
                .build();
    }

    public static RequestDto toRequestDto(Request request){
        return RequestDto.builder()
                .price(request.getPrice())
                .translatedLanguage(request.getTranslatedLanguage())
                .sourceLanguage(request.getSourceLanguage())
                .contentId(request.getContentId())
                .build();
    }

    public static RequestDto toRequestDto(RequestInfo requestInfo){
        return RequestDto.builder()
                .contentId(requestInfo.getContentId())
                .sourceLanguage(requestInfo.getSourceLanguage())
                .translatedLanguage(requestInfo.getTranslatedLanguage())
                .price(requestInfo.getPrice())
                .name(requestInfo.getName())
                .status(requestInfo.getStatus())
                .modifiedAt(requestInfo.getModifiedAt())
                .build();
    }
}
