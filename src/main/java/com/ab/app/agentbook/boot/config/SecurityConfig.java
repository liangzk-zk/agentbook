/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月26日
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //认证用户来源【内存】
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        //释放静态资源，指定资源拦截，指定自定义认证页面，指定退出认证配置，crsf配置
        http.authorizeRequests()
        .antMatchers("/login.jsp","fail.jsp","/resources/**",
                "/rest/mobile/**",
              "/swagger-resources/configuration/ui",//用来获取支持的动作
              "/swagger-resources",//用来获取api-docs的URI
              "/swagger-resources/configuration/security",//安全选项
              "/swagger-ui.html",
              "/webjars/**",
              "/v2/**").permitAll()
        .antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login.jsp")
        .loginProcessingUrl("/login")
        .successForwardUrl("/main.jsp")
        .failureForwardUrl("/login.jsp")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login.jsp")
        .invalidateHttpSession(true)
        .permitAll()
        .and()
        .csrf()
        .disable();
    }
}
