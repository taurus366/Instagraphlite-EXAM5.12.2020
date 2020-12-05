package softuni.exam.instagraphlite.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

public class ValidatorUtilImpl implements ValidatorUtil{

    @Autowired
    private final Validator validator;

    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> boolean isValid(T entity) {
        return validator.validate(entity).isEmpty();
    }
}
