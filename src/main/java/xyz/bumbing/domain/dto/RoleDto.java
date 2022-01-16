package xyz.bumbing.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.entity.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RoleDto implements Serializable {
    private  Long id;
    private String name;
    private List<PrivilegeDto> privileges = new ArrayList<>();

    public static RoleDto of(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.id = role.getId();
        roleDto.name = role.getName();
        roleDto.privileges.addAll(role.getRolePrivileges().stream().map(s->s.getPrivilege()).map(PrivilegeDto::of).collect(Collectors.toList()));
        return roleDto;
    }
}
