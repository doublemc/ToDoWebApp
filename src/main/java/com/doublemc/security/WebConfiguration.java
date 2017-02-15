package com.doublemc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by michal on 31.01.17.
 */
@EnableWebSecurity
@Configuration
public class WebConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // allow everyone to register an account
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll();

        //console is just for testing
        http.authorizeRequests().antMatchers("/console/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        // making H2 console working
        http.headers().frameOptions().disable();
        http.httpBasic();
        /*
        https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html#when-to-use-csrf-protection
        for non-browser APIs there is no need to use csrf protection
        */
        http.csrf().disable();
    }
}
