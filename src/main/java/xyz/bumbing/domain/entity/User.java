package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.dto.UserDto;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Login login;

    //== 생성 메서드 == //


    public static User createUser(UserDto.CreateUserDto createUserDto) {
        Address address = Address.builder()
                .zipCode(createUserDto.getAddress().getZipCode())
                .address1(createUserDto.getAddress().getAddress1())
                .address2(createUserDto.getAddress().getAddress2())
                .build();
        User user = new User();
        user.name = createUserDto.getName();
        user.address = address;
        user.birthDay = createUserDto.getBirthDay();
        user.password = createUserDto.getPassword();
        user.email = createUserDto.getEmail();
        user.phone = createUserDto.getPhone();
        user.gender = createUserDto.getGender();
        user.status = MemberStatusType.Y;
        return user;
    }

    public void updateUser(UserDto.UpdateUserDto updateUserDto) {
        Address address = Address.builder()
                .zipCode(updateUserDto.getAddress().getZipCode())
                .address1(updateUserDto.getAddress().getAddress1())
                .address2(updateUserDto.getAddress().getAddress2())
                .build();
        User user = new User();
        user.address = address;
        user.password = updateUserDto.getPassword();
    }
    //== 비지니스 로직 ==//


    public void addRole(Role role) {
        if (!validateDuplicationRole(role)) {
            UserRole userRole = UserRole.builder().user(this).role(role).build();
            userRoles.add(userRole);
            role.getUserRole().add(userRole);
        }
    }

    public void removeRole(Role role) {
        userRoles.removeIf(s -> s.getUser().getId().equals(this.id) && s.getRole().getId().equals(role.getId()));
    }

    private boolean validateDuplicationRole(Role role) {
        return userRoles.stream().anyMatch(s -> s.getUser().getId().equals(this.id) && s.getRole().getId().equals(role.getId()));
    }

    public void disable() {
        this.status = MemberStatusType.N;
    }

    public void enable() {
        this.status = MemberStatusType.Y;
    }


}