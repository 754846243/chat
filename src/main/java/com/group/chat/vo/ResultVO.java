package com.group.chat.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 陈雨菲
 * @description
 * @data
 */
@Data
public class ResultVO<T> implements Serializable {

    private String code; // 错误码
    private String msg; //提示信息.
    private T data; // 具体内容.
}
