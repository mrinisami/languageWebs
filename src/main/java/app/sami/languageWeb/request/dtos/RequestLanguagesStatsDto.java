package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.request.models.RequestLanguageStats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLanguagesStatsDto {
    private List<RequestLanguageStatsDto> requestLanguageStats;
}
