package com.Aptech.userservice.Configs;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Aptech.userservice.Entitys.Users;

public class CustomUserDetails implements UserDetails {

    private final Users user;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(Users user, List<String> permissionCodes) {
        this.user = user;
        this.authorities = permissionCodes.stream()
                .map(SimpleGrantedAuthority::new) // Spring dùng để xác thực hasAuthority()
                .collect(Collectors.toList());
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.getIsActive();
    }
}
