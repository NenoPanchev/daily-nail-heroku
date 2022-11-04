package dailynailheroku.models.validators;

import dailynailheroku.models.binding.ArticleCreateBindingModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArticleImageValidator implements ConstraintValidator<ArticleImage, ArticleCreateBindingModel> {
    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(ArticleImage constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ArticleCreateBindingModel articleCreateBindingModel, ConstraintValidatorContext constraintValidatorContext) {
        boolean noUrl = articleCreateBindingModel.getImageUrl().isEmpty();
        boolean noFile = articleCreateBindingModel.getImageFile().getContentType().equals("application/octet-stream");
        boolean isValid = !noUrl || !noFile;

        if (!isValid) {

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }

}
