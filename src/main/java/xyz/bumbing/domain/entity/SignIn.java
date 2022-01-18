package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignIn extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String refreshToken;

    @Column
    private String refreshTokenExpiredDate;

    @Column(length = 45)
    private String connectIp;

    @Column
    private String deviceId;

    @Column
    private String deviceOS;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //== 생성 메서드 ==//
    public static SignIn signIn(String refreshToken, String connectIp, String deviceId, String deviceOS) {
        SignIn signIn = new SignIn();
        signIn.refreshToken = refreshToken;
        signIn.connectIp = connectIp;
        signIn.deviceId = deviceId;
        signIn.deviceOS = deviceOS;
        return signIn;
    }


    //== 연관관계 메소드 ==//
    void setUser(User user) {
        this.user = user;
    }
}
