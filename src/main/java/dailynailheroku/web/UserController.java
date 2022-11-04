package dailynailheroku.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dailynailheroku.models.binding.UserRegistrationBindingModel;
import dailynailheroku.models.binding.UserUpdateNameAndEmailBindingModel;
import dailynailheroku.models.binding.UserUpdatePasswordBindingModel;
import dailynailheroku.models.dtos.UserFullNameAndEmailDto;
import dailynailheroku.models.dtos.UserNewPasswordDto;
import dailynailheroku.models.service.UserServiceModel;
import dailynailheroku.services.ArticleService;
import dailynailheroku.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ArticleService articleService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, ArticleService articleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.articleService = articleService;
    }

    @ModelAttribute("userRegistrationBindingModel")
    public UserRegistrationBindingModel createBindingModel() {
        return new UserRegistrationBindingModel();
    }

    @ModelAttribute("userUpdateNameAndEmailBindingModel")
    public UserUpdateNameAndEmailBindingModel updateNameAndEmailBindingModel() {
        return new UserUpdateNameAndEmailBindingModel();
    }

    @ModelAttribute("userUpdatePasswordBindingModel")
    public UserUpdatePasswordBindingModel userUpdatePasswordBindingModel() {
        return new UserUpdatePasswordBindingModel();
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession httpSession) {

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute("email") String email,
                              RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("email", email);

        return "redirect:login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(@Valid UserRegistrationBindingModel userRegistrationBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);

            return "redirect:register";
        }

        if (this.userService.existsByEmail(userRegistrationBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userExistsError", true);
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            return "redirect:register";
        }

        UserServiceModel userServiceModel = modelMapper
                .map(userRegistrationBindingModel, UserServiceModel.class);
        userService.registerAndLoginUser(userServiceModel);
        return "redirect:/";
    }

    @GetMapping("/terms-and-conditions")
    public String terms(Model model) {
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "terms-and-conditions";
    }

    @GetMapping("/profile/settings")
    public String profile(Principal principal, Model model) {

        model.addAttribute("principalName", userService.getUserNameByEmail(principal.getName()));
        model.addAttribute("principalEmail", principal.getName());
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        return "profile-settings";
    }

    @PostMapping("/profile/settings")
    public String updateNameAndEmail(@Valid UserUpdateNameAndEmailBindingModel userUpdateNameAndEmailBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateNameAndEmailBindingModel", userUpdateNameAndEmailBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userUpdateNameAndEmailBindingModel", bindingResult);

            return "redirect:settings";
        }

        if (this.userService.existsByEmail(userUpdateNameAndEmailBindingModel.getEmail())
        && !userUpdateNameAndEmailBindingModel.getEmail().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("userExistsError", true);
            redirectAttributes.addFlashAttribute("userUpdateNameAndEmailBindingModel", userUpdateNameAndEmailBindingModel);
            return "redirect:settings";
        }


        boolean changesMade = userService.updateFullNameAndEmailIfNeeded(
                modelMapper.map(userUpdateNameAndEmailBindingModel, UserFullNameAndEmailDto.class),
                principal.getName());

        if (changesMade) {
            userService.loadPrincipal(userUpdateNameAndEmailBindingModel.getEmail());
            redirectAttributes.addFlashAttribute("successfullyUpdated", true);
        }

        return "redirect:/";
    }

    @GetMapping("/profile/change-password")
    public String profilePassword(Principal principal, Model model) {

        model.addAttribute("principalName", userService.getUserNameByEmail(principal.getName()));
        model.addAttribute("principalEmail", principal.getName());
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        return "change-password";
    }

    @PostMapping("/profile/change-password")
    public String updatePassword(@Valid UserUpdatePasswordBindingModel userUpdatePasswordBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Principal principal) {

        if (!userService.passwordMatches(principal.getName(), userUpdatePasswordBindingModel.getOldPassword())) {
            redirectAttributes.addFlashAttribute("incorrectPassword", true);
            return "redirect:change-password";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdatePasswordBindingModel", userUpdatePasswordBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userUpdatePasswordBindingModel", bindingResult);

            return "redirect:change-password";
        }

        if (userUpdatePasswordBindingModel.getOldPassword().equals(userUpdatePasswordBindingModel.getNewPassword())) {
            redirectAttributes.addFlashAttribute("samePassword", true);

            return "redirect:change-password";
        }


        userService.updatePassword(modelMapper.map(userUpdatePasswordBindingModel, UserNewPasswordDto.class), principal.getName());
        userService.loadPrincipal(principal.getName());
        redirectAttributes.addFlashAttribute("successfullyUpdated", true);

        return "redirect:/";
    }
}
