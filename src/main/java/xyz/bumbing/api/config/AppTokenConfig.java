package xyz.bumbing.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.token")
@Getter
@Setter
public class AppTokenConfig {

    private String accessSecret;
    private Long accessExpired;

    private String refreshSecret;
    private Long refreshExpired;

}
