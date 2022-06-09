package com.remember.core.configs;

import com.remember.core.AuthenticationApp.services.AuthService;
import com.remember.core.AuthenticationApp.services.RememberOAuth2UserService;
import com.remember.core.exceptionHandler.OAuth2AuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * https://stackoverflow.com/questions/60968888/a-granted-authority-textual-representation-is-required-in-spring-security
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final RememberOAuth2UserService rememberOAuth2UserService;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 향후 csrf를 보완할것
        http
                .csrf().disable()
        ;

        http
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().permitAll()
        ;

        http
                .formLogin()
                .loginPage("/auth/forms/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .failureUrl("/auth/forms/login")
                .usernameParameter("username")
                .passwordParameter("password")
        ;

        http
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
        ;

        http
                .oauth2Login()
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                .userService(rememberOAuth2UserService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
