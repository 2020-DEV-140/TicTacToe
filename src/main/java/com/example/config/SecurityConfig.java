package com.example.config;

import com.example.repository.PlayerRepository;
import com.example.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl(playerRepository)).passwordEncoder(new Argon2PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().
                    disable()
                .authorizeRequests()
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        .permitAll()
                    .and()
                        .logout()
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/")
                            .permitAll()
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID")
                        .and()
                            .httpBasic()
                .authenticationEntryPoint(
                        (httpServletRequest, httpServletResponse, e) -> httpServletResponse
                                .sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()));
    }
}
