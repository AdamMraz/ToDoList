package com.config;

import com.service.impl.UserManagerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final UserManagerServiceImpl userManagerService;

    protected void configure (HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .disable()
                .anonymous()
                    .authorities("ROLE_ANON")
                .and()
                .authorizeRequests()
                    .antMatchers("/registration", "/registration/**", "/login").hasRole("ANON")
                    .antMatchers("/v2/api-docs/", "/swagger-ui.html", "/api/case/").hasRole("ADMIN")
                    .antMatchers("/table").hasRole("USER")
                    .antMatchers("/css/**", "/api/v2/case/",
                            "/js/**", "/static/css/**")
                        .hasAnyRole("ANON", "USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                    //login
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");
    }

    @Autowired
    private void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userManagerService).passwordEncoder(bCryptPasswordEncoder);
    }
}
