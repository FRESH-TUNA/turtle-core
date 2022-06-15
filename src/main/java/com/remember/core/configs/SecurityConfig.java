package com.remember.core.configs;

import com.remember.core.authentication.services.AuthService;
import com.remember.core.authentication.services.OAuth2Service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * https://stackoverflow.com/questions/60968888/a-granted-authority-textual-representation-is-required-in-spring-security
 * https://binchoo.tistory.com/46
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthService authService;
    private final OAuth2Service OAuth2Service;

//    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(WebSecurity web){
        // static 리소스 무시
        web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }// Register HttpSessionEventPublisher


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * csrf and authenticationEntryPoint
         */
        http
                .csrf().csrfTokenRepository(sessionCsrfRepository()).and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        ;

        /*
         * url 요청시 인증된 유저의 권한을 검사를 거쳐야 통과시킨다.
         */
        http
                .authorizeRequests()
                // users/me/questions
                .antMatchers("/users/me/questions/**").authenticated()
                // algorithms
                .antMatchers(HttpMethod.POST, "/algorithms").hasRole("ADMIN")
                // platforms
                .antMatchers(HttpMethod.POST, "/platforms").hasRole("ADMIN")
                // auth
                .antMatchers("/auth/**").permitAll()
        ;

        /*
         * login 곤련 처리
         */
        http
                .formLogin()
                .loginPage("/auth/forms/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
        ;

        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

        http
                .sessionManagement()
                // 동시 최대 세션
                .maximumSessions(1)
                // 동시 로그인 차단 true인경우 로그인시도시 에러를 뿜는다., false인 경우 기존 세션 만료시키고 로그인한다.(default)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry())
                .expiredUrl("/auth/forms/login?error=SESSION_EXPIRED")
        ;

        http
                .oauth2Login()
                .failureHandler(authenticationFailureHandler)
                .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                .userService(OAuth2Service); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    /*
     * beans
     */
    @Bean
    public HttpSessionCsrfTokenRepository sessionCsrfRepository() {
        HttpSessionCsrfTokenRepository csrfRepository = new HttpSessionCsrfTokenRepository();

        // HTTP 헤더에서 토큰을 인덱싱하는 문자열 설정
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        // URL 파라미터에서 토큰에 대응되는 변수 설정
        csrfRepository.setParameterName("_csrf");
        // 세션에서 토큰을 인덱싱 하는 문자열을 설정. 기본값이 무척 길어서 오버라이딩 하는 게 좋아요.
        // 기본값: "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"
        csrfRepository.setSessionAttributeName("CSRF_TOKEN");

        return csrfRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
