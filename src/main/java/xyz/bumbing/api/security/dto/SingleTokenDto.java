package xyz.bumbing.api.security.dto;

import lombok.Data;

@Data
public class SingleTokenDto {

    private String token;
    private String expiresIn;

}
