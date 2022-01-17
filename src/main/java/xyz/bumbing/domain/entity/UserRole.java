package xyz.bumbing.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role ;

    //== 생성 ==//
    @Builder
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    //== 연관관계 ==//
    public void removeRelationship(){
        this.user = null;
        this.role = null;
    }

}