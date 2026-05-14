package se.jensen.miljana.cloudstoreuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.miljana.cloudstoreuserservice.model.user.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
