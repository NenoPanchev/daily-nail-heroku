package dailynailheroku.services;

import dailynailheroku.models.dtos.UserFullNameAndEmailDto;
import dailynailheroku.models.dtos.UserNewPasswordDto;
import dailynailheroku.models.dtos.UserRoleDto;
import dailynailheroku.models.dtos.json.UserEntityExportDto;
import dailynailheroku.models.service.UserServiceModel;
import dailynailheroku.models.view.UserViewModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;
    UserServiceModel findByEmail(String email);
    boolean existsByEmail(String email);

    void registerAndLoginUser(UserServiceModel userServiceModel);

    String getUserNameByEmail(String email);

    boolean updateFullNameAndEmailIfNeeded(UserFullNameAndEmailDto userFullNameAndEmailDto, String principalEmail);

    void loadPrincipal(String email);

    boolean passwordMatches(String principalEmail, String oldPassword);

    void updatePassword(UserNewPasswordDto userNewPasswordDto, String principalEmail);
    UserServiceModel getPrincipal();

    List<String> getAllAuthorNames();

    List<UserViewModel> getAllUsersOrderedByRoles();

    UserViewModel getUserViewModelById(String id);

    void updateRole(UserRoleDto userRoleDto);
    List<UserEntityExportDto> exportUsers();

    void seedNonInitialUsers() throws FileNotFoundException;
}
