package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserMapper userMapper;

    public int insUser(UserEntity entity) {
        // 유효성 검사
        // 이메일 보내는 동안 화면

        entity.setSPassword(BCrypt.hashpw(entity.getSPassword(), BCrypt.gensalt()));
        System.out.println(entity.getSPassword());
        return userMapper.insUser(entity);
    }

    public UserEntity selUser(UserEntity entity) {
        UserEntity dbEntity = new UserEntity();
        try {
            dbEntity = userMapper.selUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(entity.getSEmail().length() == 0) {
            dbEntity = null;
        }

        return dbEntity;
    }
}
