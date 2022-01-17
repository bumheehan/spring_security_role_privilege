package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private final Set<RolePrivilege> rolePrivileges = new HashSet<>();


    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private final Set<UserRole> userRole = new HashSet<>();


    //== 생성 ==//
    @Builder
    Role(String name) {
        this.name = name;
    }


    //== 비지니스 ==//
    public void addPrivilege(Privilege privilege) {
        if (findRolePrivilege(privilege).isEmpty()) {
            RolePrivilege rolePrivilege = RolePrivilege.builder().role(this).privilege(privilege).build();
            rolePrivileges.add(rolePrivilege);
            privilege.getRolePrivileges().add(rolePrivilege);
        }
    }

    public void removePrivilege(Privilege privilege) {
        Optional<RolePrivilege> rolePrivilegeOptional = findRolePrivilege(privilege);
        rolePrivilegeOptional.ifPresent(s -> {
            s.removeRole();
            rolePrivileges.remove(s);
        });
    }

    public void replacePrivilege(Collection<Privilege> privileges) {
        Set<RolePrivilege> newList = privileges.stream().map(s -> findRolePrivilege(s).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
        rolePrivileges.clear();
        rolePrivileges.addAll(newList);
        privileges.forEach(this::addPrivilege);
    }


    private Optional<RolePrivilege> findRolePrivilege(Privilege privilege) {
        return rolePrivileges.stream().filter(s -> s.getRole().getId().equals(this.id) && s.getPrivilege().getId().equals(privilege.getId())).findAny();
    }
}

