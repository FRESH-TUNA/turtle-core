package com.remember.core.authService.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private String password;

    @Column
    private String nickname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    /*
     * methods
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Builder
    public User(Long id, String password, String username, String nickname) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }
}
