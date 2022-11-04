package dailynailheroku.services;

import dailynailheroku.models.binding.ArticleEditBindingModel;
import dailynailheroku.models.binding.ArticleSearchBindingModel;
import dailynailheroku.models.dtos.json.ArticleEntityExportDto;
import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.service.ArticleCreateServiceModel;
import dailynailheroku.models.service.ArticleServiceModel;
import dailynailheroku.models.view.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;

    ArticlesPageViewModel getAllArticlesForAdminPanel();

    ArticlesPageViewModel getAllArticlesForAdminPanel(Integer page);
    ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now);
    ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now, Integer page);

    List<String> getTimePeriods();

    List<String> getArticleStatuses();

    ArticlesPageViewModel getFilteredArticles(ArticleSearchBindingModel articleSearchBindingModel);

    ArticleEditBindingModel getArticleEditBindingModelById(String id);

    void deleteArticle(String id);

    void editArticle(ArticleEditBindingModel articleEditBindingModel) throws IOException;

    ArticlePreViewModel getNewestArticleByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now);

    List<ArticlePreViewModel> getFourArticlesByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now);

    List<ArticlePreViewModel> getLatestFiveArticles(LocalDateTime now);

    List<ArticlePreViewModel> getLatestNineArticles(LocalDateTime now);
    List<ArticlePreViewModel> getFiveMostPopular();

    void setTopFalse(String poppedOutId);

    void setTopTrue(String id);

    List<ArticlePreViewModel> getTopArticles(LocalDateTime now);

    List<String> getAllTopArticlesIds(LocalDateTime now);

    ArticleViewModel getArticleViewModelByUrl(String url);

    ArticleServiceModel getArticleById(String id);
    String getArticleUrlById(String id);

    String getArticleUrlByCommentId(String id);

    List<ArticleEntityExportDto> exportArticles();
    void seedArticles() throws FileNotFoundException;
    ArticleServiceModel getArticleByUrl(String url);
    LocalDateTime getLocalDateTimeFromString(String time);
    boolean hasArticles();
    void increaseSeenByOne(String id, Integer seen);
    List<CategoryViewsCountModel> getCategoryViews();
}
