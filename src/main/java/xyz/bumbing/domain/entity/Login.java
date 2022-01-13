package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Login extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String refreshToken;

    @Column(length = 45)
    private String connectIp;

    @Column
    private String deviceId;

    @Column
    private String deviceOS;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private  User user;

    @Builder
    public Login(String connectIp, String refreshToken) {
        this.refreshToken = refreshToken;
        this.connectIp = connectIp;
    }
}
