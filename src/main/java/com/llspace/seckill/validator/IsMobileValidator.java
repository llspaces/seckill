package com.llspace.seckill.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.llspace.seckill.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
/**
 * <p>@filename IsMobileValidator</p>
 * <p>
 * <p>@description 自定义IsMobile Validator</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 17:23
 **/

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {
            return ValidatorUtil.isMobile(value);
        }else {
            if(StringUtils.isEmpty(value)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }

}

