package xyz.bumbing.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoginDto implements Serializable {
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Long id;
    private final String refreshToken;
    private final String connectIp;
    private final String deviceId;
    private final String deviceOS;
    private final UserDto user;
}
