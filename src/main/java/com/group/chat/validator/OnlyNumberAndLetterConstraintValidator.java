package com.group.chat.validator;

import com.group.chat.constraint.OnlyNumberAndLetterConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 陈雨菲
 * @description 字符串只能包括数字和字母
 * @data 2019/12/9
 */
public class OnlyNumberAndLetterConstraintValidator implements ConstraintValidator<OnlyNumberAndLetterConstraint, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String str = o.toString();
        return str.matches("^[a-z0-9A-Z]+$");
    }
}
