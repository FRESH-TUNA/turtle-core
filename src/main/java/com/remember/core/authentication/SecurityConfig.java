package com.remember.core.authentication;

import com.remember.core.authentication.authToken.AuthTokenFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthTokenFilter authTokenFilter;

    //private final AuthenticationEntryPoint authenticationEntryPoint;
    //private final AuthenticationFailureHandler authenticationFailureHandler;
    //private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Value("${cors.allowed-origins}")
    private String corsAllowedHeaders;

    @Value("${cors.allowed-methods}")
    private String corsAllowedMethods;

    @Value("${cors.allowed-origins}")
    private String corsAllowedOrigins;

    @Value("${cors.exposed-headers}")
    private String corsExposedHeaders;

    @Value("${cors.max-age}")
    private Long corsMaxAge;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
         * session, csrf settings
         */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().disable();

        /*
         * formlogin, httpbasic, logout settings
         */
        http
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();

        /*
         * add jwt Token Filter
         */
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);


        /*
         * authorization filter
         */
        http
                .authorizeRequests()
                // cors preflight permit
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // users/me/questions
                .antMatchers("/users/me/questions/**").authenticated()
                // algorithms
                .antMatchers(HttpMethod.POST, "/algorithms").hasRole("ADMIN")
                // platforms
                .antMatchers(HttpMethod.POST, "/platforms").hasRole("ADMIN")
                // auth
                .antMatchers("/auth/**").permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedHeaders(Arrays.asList(corsAllowedHeaders.split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsAllowedMethods.split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsAllowedOrigins.split(",")));
        corsConfig.setExposedHeaders(Arrays.asList(corsExposedHeaders.split(",")));
        corsConfig.setMaxAge(corsMaxAge);
        corsConfig.setAllowCredentials(true);

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
