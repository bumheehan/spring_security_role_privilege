package xyz.bumbing.api.service.dto;

import lombok.Builder;
import lombok.Data;
import xyz.bumbing.domain.dto.AddressDto;
import xyz.bumbing.domain.dto.LoginDto;
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
}