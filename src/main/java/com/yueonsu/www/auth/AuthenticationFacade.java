package com.yueonsu.www.auth;

import com.yueonsu.www.user.UserMapper;
import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthenticationFacade {

    @Autowired private UserMapper mapper;

    public UserEntity getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uid = auth.getName();
        UserEntity entity = new UserEntity();
        entity.setSId(uid);
        return mapper.selUser(entity);
    }

    public int getLoginUserPk() {
        return (getLoginUser() != null) ? getLoginUser().getNUserSeq() : 0;
    }
}
