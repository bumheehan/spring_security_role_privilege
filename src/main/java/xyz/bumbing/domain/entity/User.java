package xyz.bumbing.domain.entity;

import lombok.Builder;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberStatusType status;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column
    private LocalDate birthDay;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private final List<UserRole> userRoles = new ArrayList<>();

    @OneToOne(mappedBy = "user",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Login login;

    //== 생성 메서드 == //

    @Builder
    public User(String name, String email, String password, String phone, MemberStatusType status, Address address, GenderType gender, LocalDate birthDay) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
        this.address = address;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    //== 비지니스 로직 ==//
    public void changePassword(String password) {
        this.password = password;
    }

    public void appendRole(Role role){
        if(!validateDuplicationRole(role)){
            UserRole userRole = UserRole.builder().user(this).role(role).build();
            userRoles.add(userRole);
            role.getUserRole().add(userRole);
        }
    }
    public void removeRole(Role role){
        userRoles.removeIf(s -> s.getUser().getId().equals(this.id) && s.getRole().getId().equals(role.getId()));
    }
    private boolean validateDuplicationRole(Role role){
        return userRoles.stream().anyMatch(s -> s.getUser().getId().equals(this.id) && s.getRole().getId().equals(role.getId()));
    }

    public void disable() {
        this.status = MemberStatusType.N;
    }

    public void enable() {
        this.status = MemberStatusType.Y;
    }


}