package xyz.bumbing.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.bumbing.domain.entity.Privilege;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PrivilegeDto implements Serializable {
        private Long id;
        private String name;

        public static PrivilegeDto of(Privilege privilege){
        PrivilegeDto privilegeDto = new PrivilegeDto();
        privilegeDto.id=privilege.getId();
        privilegeDto.name=privilege.getName();
        return privilegeDto;
    }
}
