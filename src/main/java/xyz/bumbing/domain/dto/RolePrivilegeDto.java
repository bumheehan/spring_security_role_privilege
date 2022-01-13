package xyz.bumbing.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePrivilegeDto implements Serializable {
    private final Long id;
    private final PrivilegeDto privilege;
    private final RoleDto role;
}
