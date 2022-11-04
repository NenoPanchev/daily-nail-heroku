package dailynailheroku.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.services.ArticleService;
import dailynailheroku.services.JokeService;

import java.time.LocalDateTime;

@Controller
public class HomeController {
    private final ArticleService articleService;
    private final JokeService jokeService;

    public HomeController(ArticleService articleService, JokeService jokeService) {
        this.articleService = articleService;
        this.jokeService = jokeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("topSport", articleService.getNewestArticleByCategoryName(CategoryNameEnum.SPORTS, now));
        model.addAttribute("sports", articleService.getFourArticlesByCategoryName(CategoryNameEnum.SPORTS, now));
        model.addAttribute("topEntertainment", articleService.getNewestArticleByCategoryName(CategoryNameEnum.ENTERTAINMENT, now));
        model.addAttribute("entertainments", articleService.getFourArticlesByCategoryName(CategoryNameEnum.ENTERTAINMENT, now));
        model.addAttribute("topWorld", articleService.getNewestArticleByCategoryName(CategoryNameEnum.WORLD, now));
        model.addAttribute("world", articleService.getFourArticlesByCategoryName(CategoryNameEnum.WORLD, now));
        model.addAttribute("topCovid", articleService.getNewestArticleByCategoryName(CategoryNameEnum.COVID_19, now));
        model.addAttribute("covid", articleService.getFourArticlesByCategoryName(CategoryNameEnum.COVID_19, now));
        model.addAttribute("latestFive", articleService.getLatestFiveArticles(now));
        model.addAttribute("latestNine", articleService.getLatestNineArticles(now));
        model.addAttribute("popular", articleService.getFiveMostPopular());
        model.addAttribute("topArticles", articleService.getTopArticles(now));
        model.addAttribute("topBusiness", articleService.getNewestArticleByCategoryName(CategoryNameEnum.BUSINESS, now));
        model.addAttribute("business", articleService.getFourArticlesByCategoryName(CategoryNameEnum.BUSINESS, now));
        model.addAttribute("topTechnology", articleService.getNewestArticleByCategoryName(CategoryNameEnum.TECHNOLOGY, now));
        model.addAttribute("technology", articleService.getFourArticlesByCategoryName(CategoryNameEnum.TECHNOLOGY, now));
        model.addAttribute("joke", jokeService.getLatestJoke());

        return "index";
    }
}
