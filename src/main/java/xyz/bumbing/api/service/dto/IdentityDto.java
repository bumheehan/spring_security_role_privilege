package xyz.bumbing.api.service.dto;

import xyz.bumbing.exercise.type.GenderType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class IdentityDto {
    private String name;
    private String phone;
    private GenderType gender;
    private String birth;
    private String ci;
    private String di;
}
