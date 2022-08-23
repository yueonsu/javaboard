package com.yueonsu.www.config.security;

import com.yueonsu.www.user.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class DefaultUserDetails implements UserDetails {

    @Autowired private UserEntity entity;

    public UserEntity getMember() {
        return entity;
    }

    public int getLoginUserPk() {
        return entity.getNUserSeq();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return entity.getSPassword();
    }

    @Override
    public String getUsername() {
        return entity.getSId();
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
}
