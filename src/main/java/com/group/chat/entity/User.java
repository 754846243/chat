package com.group.chat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 陈雨菲
 * @description 用户实体类
 * @data 2019/12/9
 */
@Entity
@Data
@Table(name = "user")
public class User {

    /* 用户id，递增自动生成 */
    @Id
    @GeneratedValue
    private Integer id;

    /* 用户姓名 */
    private String username;

    /* 用户密码 */
    private String password;

    /* 用户性别 */
    private String gender;

    /* 用户年龄 */
    private Integer age;

    /* 用户头像地址 */
    private String avatar;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
