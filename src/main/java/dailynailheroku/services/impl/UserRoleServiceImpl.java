package dailynailheroku.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import dailynailheroku.exceptions.ObjectNotFoundException;
import dailynailheroku.models.entities.UserRoleEntity;
import dailynailheroku.models.entities.enums.Role;
import dailynailheroku.models.service.UserRoleServiceModel;
import dailynailheroku.repositories.UserRoleRepository;
import dailynailheroku.services.UserRoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUserRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity admin = new UserRoleEntity().setRole(Role.ADMIN);
            UserRoleEntity editor = new UserRoleEntity().setRole(Role.EDITOR);
            UserRoleEntity reporter = new UserRoleEntity().setRole(Role.REPORTER);
            UserRoleEntity user = new UserRoleEntity().setRole(Role.USER);

            this.userRoleRepository.saveAll(List.of(admin, editor, reporter, user));
        }
    }

    @Override
    public UserRoleServiceModel findByRole(Role role) {
        return userRoleRepository.findByRole(role)
                .map(userRole -> modelMapper.map(userRole, UserRoleServiceModel.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public List<UserRoleServiceModel> findAllByRoleIn(Role... roles) {
        return userRoleRepository.findAllByRoleIn(roles)
                .stream()
                .map(entity -> modelMapper.map(entity, UserRoleServiceModel.class))
                .collect(Collectors.toList());
    }


}
