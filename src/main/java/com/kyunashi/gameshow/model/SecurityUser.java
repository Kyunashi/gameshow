package com.kyunashi.gameshow.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

/**
 * class maps user(class) data from database to the UserDetails which spring uses for authorization
 */
public class SecurityUser implements UserDetails {

    User user;

    public SecurityUser(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user
                .getRoles()
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public int getId(){
        return user.getUserId();
    }

    public boolean hasAuthority(String authority) {
        return getAuthorities().contains(new SimpleGrantedAuthority(authority));
    }
    public Instant getCreated() {
        return user.getCreated();
    }

    public String getEmail() {
        return user.getEmail();
    }
}
