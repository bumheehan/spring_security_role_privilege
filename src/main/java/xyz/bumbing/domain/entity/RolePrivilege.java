package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RolePrivilege {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role ;

    @Builder
    public RolePrivilege(Privilege privilege, Role role) {
        this.privilege = privilege;
        this.role = role;
    }
}