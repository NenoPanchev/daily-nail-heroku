package dailynailheroku.models.service;

import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.entities.enums.SubcategoryNameEnum;

public class SubcategoryServiceModel extends BaseServiceModel {
    private SubcategoryNameEnum subcategoryName;
    private CategoryNameEnum categoryCategoryName;

    public SubcategoryServiceModel() {
    }

    public SubcategoryNameEnum getSubcategoryName() {
        return subcategoryName;
    }

    public SubcategoryServiceModel setSubcategoryName(SubcategoryNameEnum subcategoryName) {
        this.subcategoryName = subcategoryName;
        return this;
    }

    public CategoryNameEnum getCategoryCategoryName() {
        return categoryCategoryName;
    }

    public SubcategoryServiceModel setCategoryCategoryName(CategoryNameEnum categoryCategoryName) {
        this.categoryCategoryName = categoryCategoryName;
        return this;
    }
}
