package com.group.chat.security;

import com.group.chat.filter.VerificationCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

import javax.sql.DataSource;

/**
 * @author 陈雨菲
 * @description
 * @data 2019/12/9
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 处理登录成功
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    // 处理登录失败
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 处理退出成功
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    // 密码加密
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    // 配置tokenRepository
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private VerificationCodeFilter verificationCodeFilter;

    @Bean
    public PersistentTokenRepository persistentTokenRepository () {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        以下的建表语句只要运行一次就可以了！！！
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 禁用csrf保护
        http.csrf().disable();

        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                // 处理未登录的情况
                .loginPage("/authentication")
                // 登录的接口
                .loginProcessingUrl("/user/login")
                // 登录成功
                .successHandler(authenticationSuccessHandler)
                // 登录失败
                .failureHandler(authenticationFailureHandler)
                // 配置记住我功能
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60 * 24)
                .userDetailsService(userDetailsService)
                // 配置需要验证的url
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(
                        "/authentication", // 未登录接口
                        "/user/login",  // 登录接口
                        "/user/register",  // 注册接口
                        "/verificationCode" // 验证码接口
                ).permitAll()
                .anyRequest()
                .authenticated()
                // 配置退出
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(logoutSuccessHandler);
    }
}
