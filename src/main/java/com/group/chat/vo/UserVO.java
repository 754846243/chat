package com.group.chat.vo;

import com.group.chat.constraint.LetterConstraint;
import com.group.chat.constraint.NumberConstraint;
import com.group.chat.constraint.OnlyNumberAndLetterConstraint;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author 陈雨菲
 * @description
 * @data
 */
@Data
public class UserVO {
    /* 用户姓名 */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /* 用户密码 */
    @Size(min=8, max=16, message="密码的长度应该在8到16之间")
    @LetterConstraint(message = "密码必须包括字母")
    @NumberConstraint(message = "密码必须包含数字")
    @OnlyNumberAndLetterConstraint(message = "密码不能包含特殊字符")
    private String password;
}
