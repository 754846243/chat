package com.group.chat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈雨菲
 * @description 处理未登录的情况
 * @data 2019/12/9
 */
@RestController
public class AuthenticationController {

    @RequestMapping("/authentication")
    public void authentication (HttpServletResponse response) throws IOException {
        response.setStatus(401);
    }
}
