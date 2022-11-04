package dailynailheroku.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dailynailheroku.models.binding.CommentCreateBindingModel;
import dailynailheroku.models.service.CommentServiceModel;
import dailynailheroku.services.ArticleService;
import dailynailheroku.services.CommentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final ArticleService articleService;

    public CommentController(CommentService commentService, ModelMapper modelMapper, ArticleService articleService) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.articleService = articleService;
    }

    @PostMapping("/add/{id}")
    public String add(@Valid CommentCreateBindingModel commentCreateBindingModel,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes,
                      @PathVariable("id") String id) {
        String url = articleService.getArticleUrlById(id);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentCreateBindingModel", commentCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentCreateBindingModel", bindingResult);
            return "redirect:/articles/a/" + url;
        }

        commentService.add(modelMapper.map(commentCreateBindingModel, CommentServiceModel.class), id);
        return "redirect:/articles/a/" + url;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        String url = articleService.getArticleUrlByCommentId(id);
        commentService.delete(id);
        return "redirect:/articles/a/" + url;
    }

    @ModelAttribute
    public CommentCreateBindingModel commentCreateBindingModel() {
        return new CommentCreateBindingModel();
    }
}
