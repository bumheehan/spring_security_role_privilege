package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<RolePrivilege> rolePrivileges = new HashSet<>();

    //== 생성 메서드 ==//
    @Builder
    public Privilege(String name) {
        this.name = name;
    }
}