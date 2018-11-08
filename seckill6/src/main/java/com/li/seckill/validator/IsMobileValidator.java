package com.li.seckill.validator;

import com.li.seckill.util.ValidatorUtil;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required= false;
    @Override
    public void initialize(IsMobile isMobile) {
        required=isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(value);
        }
        else{
            if(StringUtils.isEmpty(value)){
                return false;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
