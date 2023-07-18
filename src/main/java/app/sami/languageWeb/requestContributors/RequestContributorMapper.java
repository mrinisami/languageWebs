package app.sami.languageWeb.requestContributors;

import app.sami.languageWeb.requestContributors.dtos.PostRequestContributor;
import app.sami.languageWeb.requestContributors.dtos.PostRequestContributorDto;
import app.sami.languageWeb.requestContributors.dtos.RequestContributorDto;
import app.sami.languageWeb.user.UserMapper;

import java.util.UUID;

public class RequestContributorMapper {

    public static RequestContributorDto toDto(RequestContributor requestContributor){
        return RequestContributorDto.builder()
                .id(requestContributor.getId())
                .requestId(requestContributor.getRequestId())
                .userDto(UserMapper.toUserDto(requestContributor.getUser()))
                .contribution(requestContributor.getContribution())
                .build();
    }

    public static RequestContributor toRequestContributor(PostRequestContributorDto request, Long requestId, UUID subject){
        return RequestContributor.builder()
                .requestId(requestId)
                .contribution(request.getContribution())
                .userId(subject)
                .build();
    }

    public static PostRequestContributor toPostRequestDto(RequestContributor requestContributor){
        return PostRequestContributor.builder()
                .contribution(requestContributor.getContribution())
                .id(requestContributor.getId())
                .build();
    }
}
