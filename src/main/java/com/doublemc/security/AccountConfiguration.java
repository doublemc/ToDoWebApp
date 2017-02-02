package com.doublemc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by michal on 31.01.17.
 */

// Spring Security uses accounts from our database
@Configuration
public class AccountConfiguration extends GlobalAuthenticationConfigurerAdapter {
    private UserDetailsService userAuthService;

    @Autowired
    public AccountConfiguration(UserDetailsService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService);
    }
}
