package com.remember.core.authentication;

import com.remember.core.authentication.OAuth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.remember.core.authentication.authToken.AuthTokenFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final AuthTokenFilter authTokenFilter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final SimpleUrlAuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    //private final AuthenticationEntryPoint authenticationEntryPoint;
    //private final AuthenticationFailureHandler authenticationFailureHandler;
    //private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    @Value("${cors.allowed-headers}")
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
         * session, csrf, cors settings
         */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable()
            .cors().configurationSource(corsConfigurationSource()); //To make Spring Security bypass preflight requests

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
         * oauth settings
         */
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
            .and().userInfoEndpoint()
                .userService(oAuth2UserService)
                .and().redirectionEndpoint().baseUri("/*/oauth2/code/*")
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);

        /*
         * authorization filter
         */
        http
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                .authorizeRequests()
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
