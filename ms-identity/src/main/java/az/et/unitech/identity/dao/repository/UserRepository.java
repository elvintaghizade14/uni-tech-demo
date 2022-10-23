package az.et.unitech.identity.dao.repository;

import az.et.unitech.identity.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameEqualsIgnoreCase(String username);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

}
