package com.remember.core.authentication.domains;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private String username;

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

    public void checkPassword(String password) {
        if(!this.password.equals(password))
            throw new RuntimeException("not password equal");
    }
}
