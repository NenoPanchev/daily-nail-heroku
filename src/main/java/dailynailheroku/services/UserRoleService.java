package dailynailheroku.services;

import dailynailheroku.models.entities.enums.Role;
import dailynailheroku.models.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    void seedUserRoles();
    UserRoleServiceModel findByRole(Role role);
    List<UserRoleServiceModel> findAllByRoleIn(Role... roles);
}
