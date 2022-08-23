package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired private UserMapper userMapper;

    public int insUser(UserEntity entity) {
        // 유효성 검사
        boolean isName = Pattern.matches("^[가-힣]{2,5}$", entity.getSName());
        boolean isId = Pattern.matches("^[a-zA-Z0-9]{5,20}", entity.getSId());
        boolean isPassword = Pattern.matches("^(?=.*[a-zA-Z])(?=.*[_~!@#])(?=.*[0-9]).{6,20}$", entity.getSPassword());
        boolean isEmail = Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$\n", entity.getSEmail());

        // 한개라도 false가 있으면 회원가입 실패
        if(!isName || !isId || !isPassword || !isEmail) {
            return 0;
        }

        entity.setSPassword(BCrypt.hashpw(entity.getSPassword(), BCrypt.gensalt()));
        return userMapper.insUser(entity);
    }

    public UserEntity selUser(UserEntity entity) {
        UserEntity dbEntity = new UserEntity();
        try {
            dbEntity = userMapper.selUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbEntity;
    }
}
