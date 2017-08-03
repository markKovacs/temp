package com.codecool.appsystem.admin.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Holds the info for a authenticated user (Principal)
 */
public class AuthenticatedUser implements UserDetails {

    private final String email;
    private final String name;
    private final String token;
    private final Collection<? extends GrantedAuthority> authorities;

    AuthenticatedUser(String email, String name, String token, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.name = name;
        this.token = token;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public String getName() {
        return name;
    }
}

