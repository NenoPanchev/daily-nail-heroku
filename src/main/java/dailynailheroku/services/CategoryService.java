package dailynailheroku.services;

import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.service.CategoryServiceModel;
import dailynailheroku.models.service.CategoryServiceSeedModel;

import java.util.List;

public interface CategoryService {
    void seedCategories();
    CategoryServiceSeedModel findByCategoryName(CategoryNameEnum categoryNameEnum);
    CategoryServiceModel findByCategoryNameStr(String categoryName);
    List<String> getAllCategories();
}
