package xyz.bumbing.api.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import xyz.bumbing.api.security.dto.SingleTokenDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {
    private Long id;
    private String role;
    private SingleTokenDto access;
    private SingleTokenDto refresh;
}