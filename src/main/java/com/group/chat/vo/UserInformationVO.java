package com.group.chat.vo;

import com.group.chat.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/13
 */
@Data
public class UserInformationVO {
    private String username;

    private Integer userId;

    private Integer age;

    private String gender;

    private List<String> impressions;

    public UserInformationVO(User user) {
        this.username = user.getUsername();
        this.userId = user.getId();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.impressions = new ArrayList<>();
    }
}
