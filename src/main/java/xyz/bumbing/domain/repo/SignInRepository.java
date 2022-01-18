package xyz.bumbing.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.bumbing.domain.entity.SignIn;

import java.util.Optional;

@Repository
public interface SignInRepository extends JpaRepository<SignIn, Long> {

    Optional<SignIn> findByRefreshToken(String refreshToken);
}
