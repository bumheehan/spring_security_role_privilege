package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Privilege {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "privilege",cascade =CascadeType.ALL,orphanRemoval = true)
    private final List<RolePrivilege> rolePrivileges = new ArrayList<>();

    //== 생성 메서드 ==//
    @Builder
    public Privilege(String name) {
        this.name = name;
    }
}