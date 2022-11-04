package dailynailheroku.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dailynailheroku.models.entities.UserRoleEntity;
import dailynailheroku.models.entities.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    Optional<UserRoleEntity> findByRole(Role role);
    List<UserRoleEntity> findAllByRoleIn(Role... roles);
}
