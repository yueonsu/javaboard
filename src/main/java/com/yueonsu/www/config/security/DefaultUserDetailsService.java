package com.yueonsu.www.config.security;

import com.yueonsu.www.user.UserService;
import com.yueonsu.www.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String sId) throws UsernameNotFoundException {
        UserEntity entity = new UserEntity();
        entity.setSId(sId);
        entity = userService.selUser(entity);

        if(entity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new DefaultUserDetails(entity);
    }
}
