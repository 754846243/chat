package com.group.chat.validator;

import com.group.chat.constraint.LetterConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 陈雨菲
 * @description 字符串必须包含字母
 * @data 2019/12/9
 */
public class LetterConstraintValidator implements ConstraintValidator<LetterConstraint, Object> {

    @Override
    public void initialize(LetterConstraint constraint) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String str = o.toString();
        return str.matches(".*[A-Za-z]+.*");
    }
}
