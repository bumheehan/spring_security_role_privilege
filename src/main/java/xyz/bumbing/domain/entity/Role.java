package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private final List<RolePrivilege> rolePrivileges = new ArrayList<>();


    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private final List<UserRole> userRole = new ArrayList<>();


    //== 생성 ==//
    @Builder
    Role(String name){
        this.name= name;
    }

    //== 비지니스 ==//
    public void addPrivilege(Privilege privilege){
        if(!validatePrivilegeDuplication(privilege)){
            RolePrivilege rolePrivilege = RolePrivilege.builder().role(this).privilege(privilege).build();
            rolePrivileges.add(rolePrivilege);
            privilege.getRolePrivileges().add(rolePrivilege);
        }
    }
    public void removePrivilege(Privilege privilege){
        rolePrivileges.removeIf(s -> s.getRole().getId().equals(this.id) && s.getPrivilege().getId().equals(privilege.getId()));
    }

    public void removePrivileges(){
        for(RolePrivilege rolePrivilege : new ArrayList<>(rolePrivileges)){
            removePrivilege(rolePrivilege.getPrivilege());
        }
    }


    private boolean validatePrivilegeDuplication(Privilege privilege){
        return rolePrivileges.stream().anyMatch(s -> s.getRole().getId().equals(this.id) && s.getPrivilege().getId().equals(privilege.getId()));
    }
}