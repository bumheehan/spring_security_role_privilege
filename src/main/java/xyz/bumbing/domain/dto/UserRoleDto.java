package xyz.bumbing.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleDto implements Serializable {
    private final Long id;
    private final UserDto user;
    private final RoleDto role;
}
