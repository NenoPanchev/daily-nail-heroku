package dailynailheroku.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dailynailheroku.models.binding.JokeCreateBindingModel;
import dailynailheroku.models.service.JokeServiceModel;
import dailynailheroku.services.JokeService;

import javax.validation.Valid;

@Controller
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;
    private final ModelMapper modelMapper;

    public JokeController(JokeService jokeService, ModelMapper modelMapper) {
        this.jokeService = jokeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public String create() {
        return "joke-create";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid JokeCreateBindingModel jokeCreateBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("articleCreateBindingModel", jokeCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.jokeCreateBindingModel", bindingResult);
            return "redirect:create";
        }

        jokeService.createJoke(modelMapper.map(jokeCreateBindingModel, JokeServiceModel.class));
        return "redirect:/";
    }

    @ModelAttribute
    public JokeCreateBindingModel jokeCreateBindingModel() {
        return new JokeCreateBindingModel();
    }
}
