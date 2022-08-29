package com.yueonsu.www.user;

import com.yueonsu.www.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    /**
     * 회원가입
     * @param entity
     * @return
     */
    public int insUser(UserEntity entity) {
        /**
         * 유효성 검사
         */
        boolean isName = Pattern.matches("^[가-힣]{2,5}$", entity.getSName());
        boolean isId = Pattern.matches("^[a-zA-Z0-9]{5,20}", entity.getSId());
        boolean isPassword = Pattern.matches("^(?=.*[a-zA-Z])(?=.*[_~!@#])(?=.*[0-9]).{6,20}$", entity.getSPassword());
        boolean isEmail = Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", entity.getSEmail());

        /**
         * 한개라도 false가 있으면 회원가입 안 됨
         */
        if(!isName || !isId || !isPassword || !isEmail) {
            return 0;
        }

        /**
         * 비밀번호 암호화
         */
        entity.setSPassword(BCrypt.hashpw(entity.getSPassword(), BCrypt.gensalt()));
        return userMapper.insUser(entity);
    }

    /**
     * 로그인, 아이디 중복체크
     * @param entity
     * @return
     */
    public UserEntity selUser(UserEntity entity) {
        UserEntity dbEntity = new UserEntity();
        try {
            dbEntity = userMapper.selUser(entity);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return dbEntity;
    }
}
