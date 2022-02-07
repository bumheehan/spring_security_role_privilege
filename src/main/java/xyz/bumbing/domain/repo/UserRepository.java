package xyz.bumbing.domain.repo;

import org.springframework.data.jpa.repository.Query;
import xyz.bumbing.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value ="SELECT u FROM User u " +
            "INNER JOIN FETCH u.userRoles ur " +
            "INNER JOIN FETCH ur.role r " +
            "INNER JOIN FETCH r.rolePrivileges rp " +
            "INNER JOIN FETCH rp.privilege " +
            "WHERE u.email = :email")
    Optional<User> findOneWithPrivilegeByEmail(String email);
}






