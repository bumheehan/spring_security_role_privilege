package xyz.bumbing.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SigninDto {
    private Long id;
    private String role;
    private String accessToken;
    private String refreshToken;
}