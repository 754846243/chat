package com.group.chat.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 陈雨菲
 * @description 验证码异常
 * @data 2019/12/10
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String msg) {
        super(msg);
    }
}
