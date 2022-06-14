package com.remember.core.AuthenticationApp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * https://petrepopescu.tech/2021/01/exposing-sequential-ids-is-bad-here-is-how-to-avoid-it/
 * https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
 * https://gregor77.github.io/2021/04/21/spring-security-02/
 */
@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String email;

    @Column(unique=true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * oauth
     */

    @Column(unique=true)
    private String oauthId;

    public void oauthUserUpdate(String oauthId, String picture) {
        this.oauthId = oauthId;
        this.picture = picture;
    }

    @Builder
    private User(Long id,
                String password,
                String nickname,
                String email,
                String picture,
                Role role,
                ProviderType providerType,
                String oauthId) {
        this.id = id;
        this.oauthId = oauthId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.providerType = providerType;
    }
}
