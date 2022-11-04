package dailynailheroku.models.service;

import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.entities.enums.SubcategoryNameEnum;

import java.util.Set;

public class CategoryServiceModel extends BaseServiceModel{
    private CategoryNameEnum categoryName;
    private Set<SubcategoryNameEnum> subcategoryNames;

    public CategoryServiceModel() {
    }

    public CategoryNameEnum getCategoryName() {
        return categoryName;
    }

    public CategoryServiceModel setCategoryName(CategoryNameEnum categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Set<SubcategoryNameEnum> getSubcategoryNames() {
        return subcategoryNames;
    }

    public CategoryServiceModel setSubcategoryNames(Set<SubcategoryNameEnum> subcategoryNames) {
        this.subcategoryNames = subcategoryNames;
        return this;
    }
}
