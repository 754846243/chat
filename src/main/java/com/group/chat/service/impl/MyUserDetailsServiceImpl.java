package com.group.chat.service.impl;

import com.group.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/9
 */
@Component("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        com.group.chat.entity.User user = userService.findUserOneById(Integer.valueOf(userId));
        System.out.println(user.toString());
        if (user == null) {
            System.out.println("用户不存在");
            throw new UsernameNotFoundException(userId);
        }
        String password = user.getPassword();
        return new User(userId, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}