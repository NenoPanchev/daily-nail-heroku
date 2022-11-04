package dailynailheroku.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import dailynailheroku.models.entities.CategoryEntity;
import dailynailheroku.models.entities.SubcategoryEntity;
import dailynailheroku.models.entities.enums.CategoryNameEnum;
import dailynailheroku.models.entities.enums.SubcategoryNameEnum;
import dailynailheroku.models.service.CategoryServiceSeedModel;
import dailynailheroku.models.service.SubcategoryServiceModel;
import dailynailheroku.repositories.SubcategoryRepository;
import dailynailheroku.services.CategoryService;
import dailynailheroku.services.SubcategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public void seedSubcategories() {
        if (subcategoryRepository.count() > 0) {
            return;
        }

        Arrays.stream(SubcategoryNameEnum.values())
                .forEach(en -> {
                    SubcategoryEntity subcategoryEntity = new SubcategoryEntity()
                            .setSubcategoryName(en);
                    subcategoryRepository.save(subcategoryEntity);
                });
        attachCategoryToSubcategories(categoryService.findByCategoryName(CategoryNameEnum.SPORTS),
                subcategoryRepository.findAllBySubcategoryNameIn(SubcategoryNameEnum.FOOTBALL,
                        SubcategoryNameEnum.VOLLEYBALL,
                        SubcategoryNameEnum.TENNIS,
                        SubcategoryNameEnum.OTHER));
    }

    private void attachCategoryToSubcategories(CategoryServiceSeedModel categoryServiceSeedModel, List<SubcategoryEntity> subcategoryEntities) {
        subcategoryEntities
                .forEach(subcategoryEntity -> {
                    subcategoryEntity
                            .setCategory(modelMapper.map(categoryServiceSeedModel, CategoryEntity.class));
                    subcategoryRepository.save(subcategoryEntity);
                });
    }

    @Override
    public SubcategoryServiceModel findBySubcategoryNameEnum(SubcategoryNameEnum subcategoryNameEnum) {
        return subcategoryRepository.findBySubcategoryName(subcategoryNameEnum)
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .orElse(null);
    }

    @Override
    public SubcategoryServiceModel findBySubcategoryNameStr(String subcategoryName) {
        if (!Arrays.stream(SubcategoryNameEnum.values())
                .map(Enum::name)
        .collect(Collectors.toList())
        .contains(subcategoryName)) {
            return null;
        }
        SubcategoryServiceModel subcategoryServiceModel = subcategoryRepository.findBySubcategoryName(SubcategoryNameEnum.valueOf(subcategoryName))
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .orElse(null);

        return subcategoryServiceModel;
    }

    @Override
    public List<SubcategoryServiceModel> findAllBySubcategoryNameIn(SubcategoryNameEnum... subcategoryNameEnums) {
        return subcategoryRepository
                .findAllBySubcategoryNameIn(subcategoryNameEnums)
                .stream()
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryServiceModel> findAllByCategoryName(CategoryNameEnum categoryNameEnum) {
        return subcategoryRepository
                .findAllByCategory_CategoryName(categoryNameEnum)
                .stream()
                .map(entity -> modelMapper.map(entity, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "SubcategoryServiceImpl{}";
    }
}
