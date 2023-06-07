package app.sami.languageWeb.request.mapper;

import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.dtos.RequestLanguageStatsDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestInfo;
import app.sami.languageWeb.request.models.RequestLanguageStats;

public class RequestMapper {

    public static Request toRequest(RequestDto requestDto){
        return Request.builder()
                .price(requestDto.getPrice())
                .sourceLanguage(requestDto.getSourceLanguage())
                .translatedLanguage(requestDto.getTranslatedLanguage())
                .avgTime(requestDto.getAvgTime())
                .filePath(requestDto.getFilePath())
                .name(requestDto.getName())
                .build();
    }

    public static RequestDto toRequestDto(Request request){
        return RequestDto.builder()
                .price(request.getPrice())
                .avgTime(request.getAvgTime())
                .translatedLanguage(request.getTranslatedLanguage())
                .sourceLanguage(request.getSourceLanguage())
                .build();
    }

    public static RequestDto toRequestDto(RequestInfo requestInfo){
        return RequestDto.builder()
                .sourceLanguage(requestInfo.getSourceLanguage())
                .translatedLanguage(requestInfo.getTranslatedLanguage())
                .price(requestInfo.getPrice())
                .name(requestInfo.getName())
                .status(requestInfo.getStatus())
                .modifiedAt(requestInfo.getModifiedAt())
                .build();
    }

    public static RequestLanguageStatsDto toRequestLanguageStats(RequestLanguageStats requestLanguageStats){
        return RequestLanguageStatsDto.builder()
                .nbRequests(requestLanguageStats.getNbRequests())
                .translatedLanguage(requestLanguageStats.getTranslatedLanguage())
                .sourceLanguage(requestLanguageStats.getSourceLanguage())
                .build();
    }
}
