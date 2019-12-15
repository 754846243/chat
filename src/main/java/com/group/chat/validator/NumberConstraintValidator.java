package com.group.chat.validator;

import com.group.chat.constraint.NumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 陈雨菲
 * @description 判断字符串是否包含数字
 * @data 2019/12/9
 */
public class NumberConstraintValidator implements ConstraintValidator<NumberConstraint, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String str = o.toString();
        return str.matches(".*[0-9]+.*");
    }
}
