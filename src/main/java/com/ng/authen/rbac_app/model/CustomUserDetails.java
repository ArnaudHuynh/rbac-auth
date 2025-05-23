package com.ng.authen.rbac_app.model;

import com.ng.authen.rbac_app.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type Custom user details.
 */
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    /**
     * Instantiates a new Custom user details.
     *
     * @param UserEntity the user entity
     */
    public CustomUserDetails(UserEntity UserEntity) {
        this.userEntity = UserEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public UserEntity getUser() {
        return userEntity;
    }
}