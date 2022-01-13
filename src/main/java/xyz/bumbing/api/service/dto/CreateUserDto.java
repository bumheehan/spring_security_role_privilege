package xyz.bumbing.api.service.dto;

import lombok.Builder;
import lombok.Data;
import xyz.bumbing.domain.dto.AddressDto;
import xyz.bumbing.domain.dto.LoginDto;
import xyz.bumbing.domain.entity.Address;
import xyz.bumbing.domain.entity.User;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateUserDto {
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final AddressDto address;
    private final GenderType gender;
    private final LocalDate birthDay;


    public static User changeUser(CreateUserDto createUserDto){
        Address address = Address.builder()
                .zipCode(createUserDto.getAddress().getZipCode())
                .address1(createUserDto.getAddress().getAddress1())
                .address2(createUserDto.getAddress().getAddress2())
                .build();
        User user = User.builder()
                .name(createUserDto.getName())
                .address(address)
                .birthDay(createUserDto.getBirthDay())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phone(createUserDto.getPhone())
                .gender(createUserDto.getGender())
                .status(MemberStatusType.Y)
                .build();
        return  user;
    }
}