package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.dto.UserDto;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private SignIn signIn;

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

    public void disable() {
        this.status = MemberStatusType.N;
    }

    public void enable() {
        this.status = MemberStatusType.Y;
    }

    //== 연관관계 편의 메서드 ==//
    public void addRole(Role role) {
        if (findUserRole(role).isEmpty()) {
            UserRole userRole = UserRole.builder().role(role).user(this).build();
            userRoles.add(userRole);
            role.getUserRole().add(userRole);
        }
    }

    public void removeRole(Role role) {
        Optional<UserRole> userRoleOptional = findUserRole(role);
        if (userRoleOptional.isPresent()) {
            UserRole userRole = userRoleOptional.get();
            role.getUserRole().remove(userRole); // role table
            userRole.removeRelationship(); // mapping table
            userRoles.remove(userRole); // user table
        }
    }

    public void replacePrivilege(Collection<Role> roles) {
        Set<UserRole> newList = roles.stream().map(s -> findUserRole(s).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
        userRoles.clear();
        userRoles.addAll(newList);
        roles.forEach(this::addRole);
    }

    private Optional<UserRole> findUserRole(Role role) {
        return userRoles.stream().filter(s -> s.getUser().getId().equals(this.id) && s.getRole().getId().equals(role.getId())).findAny();
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
        signIn.setUser(this);
    }


}