package com.group.chat.filter;

import com.group.chat.component.VerificationCode;
import com.group.chat.controller.VerificationCodeController;
import com.group.chat.exception.VerificationCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈雨菲
 * @description 验证码拦截器
 * @data 2019/12/10
 */
@Component("VerificationCodeFilter")
public class VerificationCodeFilter extends OncePerRequestFilter {


    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isLogin = StringUtils.equals("/user/login", request.getRequestURI());
        boolean isPostMethod = StringUtils.equalsIgnoreCase(request.getMethod(), "post");
        if (isLogin && isPostMethod) {
            // 对于登录请求进行拦截
            System.out.println("验证码校验");
            try {
                validate(new ServletWebRequest(request));
            } catch (VerificationCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return ;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate (ServletWebRequest request){

        String sessionKey = VerificationCodeController.SESSION_KEY;
        VerificationCode codeInSession = (VerificationCode) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;

        try {
            codeInRequest  = ServletRequestUtils.getStringParameter(request.getRequest(), "code");
        } catch (ServletRequestBindingException e) {
            throw new VerificationCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new VerificationCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new VerificationCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new VerificationCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new VerificationCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }
}
