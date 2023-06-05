package app.sami.languageWeb.request.dtos;

import app.sami.languageWeb.request.dtos.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsDto {
    List<RequestDto> requests;
}
