package com.group.chat.service;


import com.group.chat.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public User findUserOne(String username);

    public User findUserOneById(Integer id);

    public User save(User user);

    public User findUserByRequest(HttpServletRequest request);
}
