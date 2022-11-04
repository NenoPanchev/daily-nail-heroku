package dailynailheroku.services;

import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.entities.enums.SubcategoryNameEnum;
import dailynailheroku.models.service.SubcategoryServiceModel;

import java.util.List;

public interface SubcategoryService {

    void seedSubcategories();
    SubcategoryServiceModel findBySubcategoryNameEnum(SubcategoryNameEnum subcategoryNameEnum);
    SubcategoryServiceModel findBySubcategoryNameStr(String  subcategoryName);
    List<SubcategoryServiceModel> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryNameEnums);
    List<SubcategoryServiceModel> findAllByCategoryName(CategoryNameEnum categoryNameEnum);
}
