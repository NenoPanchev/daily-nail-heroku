package dailynailheroku.models.entities;

import dailynailheroku.models.entities.enums.Role;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity extends BaseEntity {
        private Role role;

    public UserRoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public UserRoleEntity setRole(Role role) {
        this.role = role;
        return this;
    }
}
