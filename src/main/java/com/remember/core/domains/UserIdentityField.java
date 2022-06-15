package com.remember.core.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdentityField {
    @Column(nullable = false)
    private Long user;
}
