package app.sami.languageWeb.request.mapper;

import app.sami.languageWeb.language.models.Language;
import app.sami.languageWeb.request.dtos.PostRequestDto;
import app.sami.languageWeb.request.dtos.RequestDto;
import app.sami.languageWeb.request.dtos.RequestLanguageStatsDto;
import app.sami.languageWeb.request.models.Request;
import app.sami.languageWeb.request.models.RequestInfo;
import app.sami.languageWeb.request.models.RequestLanguageStats;
import app.sami.languageWeb.user.UserMapper;
import app.sami.languageWeb.user.models.User;
import app.sami.languageWeb.user.repos.UserRepository;

import java.time.Instant;

public class RequestMapper {

    public static PostRequestDto toPostRequestDto(Request request){
        return PostRequestDto.builder()
                .price(request.getPrice())
                .name(request.getName())
                .translatedLanguage(request.getTranslatedLanguage())
                .sourceLanguage(request.getSourceLanguage())
                .dueDate(request.getDueDate().toEpochMilli())
                .build();
    }
    public static Request toRequest(PostRequestDto postRequestDto){
        return Request.builder()
                .description(postRequestDto.getDescription())
                .translatedLanguage(postRequestDto.getTranslatedLanguage())
                .sourceLanguage(postRequestDto.getSourceLanguage())
                .filePath(postRequestDto.getFilePath())
                .name(postRequestDto.getName())
                .price(postRequestDto.getPrice())
                .dueDate(Instant.ofEpochMilli(postRequestDto.getDueDate()))
                .build();
    }

    public static Request toRequest(RequestDto requestDto){
        return Request.builder()
                .price(requestDto.getPrice())
                .sourceLanguage(Language.valueOf(requestDto.getSourceLanguage()))
                .translatedLanguage(Language.valueOf(requestDto.getTranslatedLanguage()))
                .estimatedTime(requestDto.getAvgTime())
                .filePath(requestDto.getFilePath())
                .name(requestDto.getName())
                .dueDate(requestDto.getDueDate())
                .build();
    }

    public static RequestDto toRequestDto(Request request){
        String translatedLanguage = request.getTranslatedLanguage().toString();
        String sourceLanguage = request.getSourceLanguage().toString();
        return RequestDto.builder()
                .price(request.getPrice())
                .avgTime(request.getEstimatedTime())
                .translatedLanguage(translatedLanguage.substring(0, 1).toUpperCase() + translatedLanguage.substring(1).toLowerCase())
                .sourceLanguage(sourceLanguage.substring(0, 1).toUpperCase() + sourceLanguage.substring(1).toLowerCase())
                .status(request.getStatus())
                .filePath(request.getFilePath())
                .userDto(UserMapper.toUserDto(request.getUser()))
                .dueDate(request.getDueDate())
                .id(request.getId())
                .description(request.getDescription())
                .name(request.getName())
                .build();
    }

    public static RequestDto toRequestDto(RequestInfo requestInfo){
        return RequestDto.builder()
                .sourceLanguage(requestInfo.getSourceLanguage().toString())
                .translatedLanguage(requestInfo.getTranslatedLanguage().toString())
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
