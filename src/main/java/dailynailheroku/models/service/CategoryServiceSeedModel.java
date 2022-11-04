package dailynailheroku.models.service;

import dailynailheroku.models.entities.SubcategoryEntity;
import dailynailheroku.models.entities.enums.CategoryNameEnum;

import java.util.Set;

public class CategoryServiceSeedModel extends BaseServiceModel {
    private CategoryNameEnum categoryName;
    private Set<SubcategoryEntity> subcategories;

    public CategoryServiceSeedModel() {
    }

    public CategoryNameEnum getCategoryName() {
        return categoryName;
    }

    public CategoryServiceSeedModel setCategoryName(CategoryNameEnum categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Set<SubcategoryEntity> getSubcategories() {
        return subcategories;
    }

    public CategoryServiceSeedModel setSubcategories(Set<SubcategoryEntity> subcategories) {
        this.subcategories = subcategories;
        return this;
    }
}
