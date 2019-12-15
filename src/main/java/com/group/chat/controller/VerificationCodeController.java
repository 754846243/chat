package com.group.chat.controller;

import com.group.chat.utils.VerificationCode;
import com.group.chat.utils.VerificationCodeUtil;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/10
 */
@RestController
public class VerificationCodeController {

    public static final String SESSION_KEY = "VERIFICATION_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 获取验证码
     * @param request
     */
    @GetMapping("/verificationCode")
    public void getVerificationCode (HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerificationCode verificationCode = VerificationCodeUtil.createImageCode(134, 46, 10, 4);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, verificationCode);
        ImageIO.write(verificationCode.getImage(), "jpg", response.getOutputStream());
    }
}
