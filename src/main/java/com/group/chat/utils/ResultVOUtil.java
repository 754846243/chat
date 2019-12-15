package com.group.chat.utils;


import com.group.chat.vo.ResultVO;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/9
 */
public class ResultVOUtil {
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode("200");
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(String msg, Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode("200");
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode("200");
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error (String code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
