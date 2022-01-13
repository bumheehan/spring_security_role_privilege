package xyz.bumbing.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.entity.User;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto  {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long id;
    private String name;
    private String email;
    private String phone;
    private MemberStatusType status;
    private AddressDto address;
    private GenderType gender;
    private LocalDate birthDay;

    public static UserDto of(User user){
        UserDto userDto = new UserDto();
        userDto.createdDate = user.getCreatedDate();
        userDto.modifiedDate = user.getModifiedDate();
        userDto.id = user.getId();
        userDto.name = user.getName();
        userDto.email = user.getEmail();
        userDto.phone = user.getPhone();
        userDto.status = user.getStatus();
        userDto.address = AddressDto.of(user.getAddress());
        userDto.gender = user.getGender();
        userDto.birthDay = user.getBirthDay();
        return userDto;
    }
}
