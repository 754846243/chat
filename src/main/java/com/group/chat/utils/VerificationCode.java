package com.group.chat.utils;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author 陈雨菲
 * @description 验证码类
 * @data 2019/12/10
 */
@Data
public class VerificationCode {
    /* 生成的图片 */
    private BufferedImage image;

    /* 验证码 */
    private String code;

    /* 过期时间 */
    private LocalDateTime expireTime;

    public VerificationCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried () {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
