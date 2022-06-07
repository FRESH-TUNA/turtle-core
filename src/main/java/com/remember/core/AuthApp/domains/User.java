package com.remember.core.AuthApp.domains;

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

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isOauth() {
        return !this.providerType.equals(ProviderType.LOCAL);
    }

    public void oauthUserUpdate(String username, String picture) {
        this.username = username;
        this.picture = picture;
    }

    @Builder
    public User(Long id,
                String password,
                String username,
                String email,
                String picture,
                Role role,
                ProviderType providerType) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.providerType = providerType;
    }
}
